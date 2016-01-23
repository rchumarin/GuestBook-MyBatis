package ru.icl.test.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.icl.test.dao.MessageDao;

@WebServlet(name = "DeleteMessage", urlPatterns = {"/delete"})
public class DeleteMessage extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        MessageDao messageDao = new MessageDao();
        messageDao.deleteMessage(id); 
        response.sendRedirect(request.getContextPath()+"/allmessage");
    }
    
}