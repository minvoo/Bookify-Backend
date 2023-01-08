package com.minvoo.bookify.repository;

import com.minvoo.bookify.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
