package com.example.bookproject.services;

import com.example.bookproject.models.Message;
import com.example.bookproject.models.User;
import com.example.bookproject.repositories.MessageRepository;
import com.example.bookproject.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.PageRequest;


@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void send(User sender, User receiver, String content) {
        Message msg = new Message();
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setContent(content);
        msg.setSentAt(LocalDateTime.now());
        msg.setRead(false);
        messageRepository.save(msg);
    }

    public List<Message> getInbox(User user) {
        return messageRepository.findByReceiverOrderBySentAtDesc(user);
    }

    public List<Message> getConversation(User u1, User u2) {
        return messageRepository.findByUsers(u1, u2);
    }

    public void markAsRead(User from, User to) {
        List<Message> unread = messageRepository.findBySenderAndReceiverAndIsReadFalse(from, to);
        unread.forEach(m -> m.setRead(true));
        messageRepository.saveAll(unread);
    }

    public List<Message> getInboxOverview(Long userId) {
        return messageRepository.findLatestMessagesByUserId(userId);
    }

    public List<Message> getRecentConversation(User u1, User u2, int limit) {
        List<Message> messages = messageRepository.findRecentConversation(u1, u2, PageRequest.of(0, limit));
        Collections.reverse(messages); // show oldest first
        return messages;
    }

}
