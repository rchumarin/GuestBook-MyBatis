package ru.icl.test.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WriteMessage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          
            HttpSession session = request.getSession(true);            
            String id = session.getId();
            String login = (String) session.getAttribute("login");
            boolean active = (boolean) request.getServletContext().getAttribute("active");            
            
            if(active) { 
                JSONObject jsonOb = (JSONObject) request.getServletContext().getAttribute("sessionMap");                   
                request.setAttribute("jsonOb", jsonOb);                
                request.getRequestDispatcher("pages/main.jsp").forward(request, response);
            } 
            else {
                String jstring = (String) request.getServletContext().getAttribute("jstring"); 
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                HashMap<String, List> sessionMap = gson.fromJson(jstring, HashMap.class);                                                                                                  
                request.setAttribute("jsonOb", sessionMap);    
                request.getRequestDispatcher("pages/main.jsp").forward(request, response);

            }                             
        }                                   
}

