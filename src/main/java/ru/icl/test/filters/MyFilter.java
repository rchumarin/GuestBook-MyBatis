package ru.icl.test.filters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.icl.test.dao.MessageDao;
import ru.icl.test.entity.Message;


public class MyFilter implements Filter {

    private FilterConfig filterConfig = null;
    
    private boolean active = false;
    
    String msg = null;

    public MyFilter() {
    }    
        
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain)
            throws IOException, ServletException {
    
        /* 
        Структура JSON объекта:
        { id_key : [message]} 
                
        Cтруктура GSON:
        { id_key : [message] }
        
        где: 
            id_key - сессия клиента           
            message - сообщения клиента (массив)                    
        */
                                        
        try {            
            HttpServletRequest request = (HttpServletRequest) req;             
            HttpSession session = request.getSession(true);                    
            String id = session.getId();
            String login = (String) session.getAttribute("login");                                    
            msg = request.getParameter("msg");            
            
            //session.setAttribute("login", login);            

            request.getServletContext().setAttribute("active", active);                        
            if(active) { //active - параметр фильтра со значением true
                //Обертка в JSON                      
                JSONObject jsonOb = (JSONObject) request.getServletContext().getAttribute("sessionMap");
                if (jsonOb==null) {
                    jsonOb = new JSONObject();
                }          
                //вводится массив для сообщений текущего клиента
                JSONArray jsonArr;   
                // для новой сессии создаем новый список
                if (session.isNew()) {                
                    jsonArr = new JSONArray();
                } 
                // иначе получаем список из атрибутов сессии
                else { 
                    jsonArr = (JSONArray)session.getAttribute("jmessage");                
                }
                //если параметр msg не пустой
                if (msg != null) {               
                    jsonArr.add(msg); 
                }
                session.setAttribute("jmessage", jsonArr);  
                //вывод сообщений других клиентов
                jsonOb.put(id, jsonArr);                                                 
                request.getServletContext().setAttribute("sessionMap", jsonOb);                                                              
                
            } else { //Обертка в GSON                        
                Map<String, List> sessionMap = (HashMap<String,List>)request.getServletContext().getAttribute("sessionMap");        
                if (sessionMap==null) {
                    sessionMap = new HashMap<String, List>();
                }       
                ArrayList<String> listMsg;    

                if (session.isNew()) {
                    listMsg = new ArrayList<>();            
                } else { 
                    listMsg = (ArrayList<String>) session.getAttribute("message");            
                }                
                listMsg.add(msg);            
                session.setAttribute("message", listMsg);
                sessionMap.put(id, listMsg);

                request.getServletContext().setAttribute("sessionMap", sessionMap);
                
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jstring = gson.toJson(sessionMap);
                request.getServletContext().setAttribute("jstring", jstring); 
            }             
            chain.doFilter(req, res);           
            //MyBatis           
            MessageDao messageDao = new MessageDao();            
            Message message = new Message();        
            message.setMessage(msg);
            message.setClientid(id);
            messageDao.insertMessage(message);            
  
        } catch (IOException io) {
            System.out.println ("IOException raised");
        } catch (ServletException se) {
            System.out.println ("ServletException raised");
        }        
    }                               
    
    public void destroy() {        
    }

    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;  
        String act = filterConfig.getInitParameter("active"); 
        if (act != null) 
          active = (act.toUpperCase().equals("TRUE")); 
    } 
}

