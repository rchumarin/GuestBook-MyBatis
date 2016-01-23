<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/style_main.css" rel="stylesheet" type="text/css">
         <title>Онлайн ЧАТ</title>
    </head>    
    <body>
        <div class="container">        
            <div class="logo">
                <a href="#"><img src="images/chat_.png" width="86" height="87" alt="" name="logo" /></a>
            </div>
            <div class="descr">
                <h3>Онлайн ЧАТ</h3>
            </div>
            <div class="welcome">
                <h5>Добро пожаловать, <%=session.getAttribute("login")%></h5>
                <h5 class="sessia">Ваш id: <%=session.getId()%></h5>
                <h6><a href="<%=request.getContextPath()%>/simple">< Назад&emsp;</a>
                    <a href="<%=request.getContextPath()%>">Выход</a>
                </h6>
            </div>
                               
            <div class="big_column">                           
            <div>
                <table>
                    <tr>
                        <th>id</th> 
                        <th>Users</th> 
                        <th>Messages</th>
                        <th></th>
                    </tr>                 
                    <c:forEach items="${mess}" var="msg">               
                        <tr>
                            <td>${msg.getId()}</td>
                            <td>${msg.getClientid()}</td>
                            <td><c:out value="${msg.getMessage()}"/></td>
                            <td>
                                <a href="<%=request.getContextPath()%>/delete?id=${msg.getId()}">
                                    <p style="text-align: center;"><img src="images/x.gif" width="26" height="26" alt="Delete" name="logo" /></p>
                                </a>
                            </td>       
                        </tr>   
                    </c:forEach>                    
                </table>                
    <div class="footer">© 2015 ICL. Test project</div>    
    </body>   
</html>

