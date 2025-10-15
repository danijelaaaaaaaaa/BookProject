package com.example.bookproject.repositories;

import com.example.bookproject.models.Message;
import com.example.bookproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {


    List<Message> findByReceiverOrderBySentAtDesc(User receiver);

    List<Message> findBySenderAndReceiverAndIsReadFalse(User sender, User receiver);

    @Query("SELECT m FROM Message m WHERE (m.sender = :u1 AND m.receiver = :u2) OR (m.sender = :u2 AND m.receiver = :u1) ORDER BY m.sentAt ASC")
    List<Message> findByUsers(@Param("u1") User u1, @Param("u2") User u2);

    @Query("SELECT m FROM Message m WHERE m.id IN (" +
            "SELECT MAX(m2.id) FROM Message m2 " +
            "WHERE m2.sender.id = :userId OR m2.receiver.id = :userId " +
            "GROUP BY CASE WHEN m2.sender.id = :userId THEN m2.receiver.id ELSE m2.sender.id END" +
            ") ORDER BY m.sentAt DESC")
    List<Message> findLatestMessagesByUserId(@Param("userId") Long userId);

    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender = :u1 AND m.receiver = :u2) OR (m.sender = :u2 AND m.receiver = :u1) " +
            "ORDER BY m.sentAt DESC")
    List<Message> findRecentConversation(@Param("u1") User u1, @Param("u2") User u2, org.springframework.data.domain.Pageable pageable);


}