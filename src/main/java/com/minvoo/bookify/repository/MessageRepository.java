package com.minvoo.bookify.repository;

import com.minvoo.bookify.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
