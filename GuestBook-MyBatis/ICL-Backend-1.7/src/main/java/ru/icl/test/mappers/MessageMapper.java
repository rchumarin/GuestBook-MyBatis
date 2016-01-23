package ru.icl.test.mappers;

import java.util.List;
import ru.icl.test.entity.Message;

public interface MessageMapper {
    public void insertMessage(Message message);
    public List<Message> getAllMessages();
    public void deleteMessage(Integer id);
}
