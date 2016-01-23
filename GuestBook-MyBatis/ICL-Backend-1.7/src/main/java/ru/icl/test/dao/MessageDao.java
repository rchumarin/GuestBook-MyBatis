package ru.icl.test.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import ru.icl.test.entity.Message;
import ru.icl.test.mappers.MessageMapper;

public class MessageDao { 
    
    public void insertMessage(Message message) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try{
            MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
            messageMapper.insertMessage(message);
            sqlSession.commit();
        }finally{
            sqlSession.close();
        }
    }

    public List<Message> getAllMessages() {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try{
            MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
            return messageMapper.getAllMessages();
        }finally{
            sqlSession.close();
        }
    }

    public void deleteMessage(Integer id) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try{
            MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
            messageMapper.deleteMessage(id);
            sqlSession.commit();
        }finally{
            sqlSession.close();
        }
    }
}
