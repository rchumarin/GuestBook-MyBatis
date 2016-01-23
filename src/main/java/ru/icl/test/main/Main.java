package ru.icl.test.main;

import java.util.List;
import ru.icl.test.dao.MessageDao;
import ru.icl.test.entity.Message;

public class Main {

    public static void main(String[] args) {
        MessageDao messageDao = new MessageDao(); 
        
        Message message = new Message();
        message.setMessage("БЛА_БЛА_БЛА");
        message.setClientid("JamesBond-007");
        messageDao.insertMessage(message);

        List<Message> messages = messageDao.getAllMessages();
        for (Message msg : messages) 
        {
            System.out.println(msg);
        }        
    }
    
}
