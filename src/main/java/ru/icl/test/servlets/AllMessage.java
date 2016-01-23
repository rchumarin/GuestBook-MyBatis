package ru.icl.test.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.icl.test.entity.Message;
import ru.icl.test.entity.MessageList;

@WebServlet(name="AllMessage", urlPatterns = {"/allmessage"})
public class AllMessage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {            
            MessageList messageList = new MessageList();                                     
            request.setAttribute("mess", messageList.getMessageList());            
  
        } catch (SQLException ex) {
            Logger.getLogger(AllMessage.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        request.getRequestDispatcher("pages/allmessage.jsp").forward(request, response);

        }
}
