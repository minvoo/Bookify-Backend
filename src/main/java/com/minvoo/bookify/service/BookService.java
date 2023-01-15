package com.minvoo.bookify.service;

import com.minvoo.bookify.model.Book;
import com.minvoo.bookify.model.Checkout;
import com.minvoo.bookify.repository.BookRepository;
import com.minvoo.bookify.repository.CheckoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;

    private CheckoutRepository checkoutRepository;

    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public Book checkoutBook(String userEmail, Long bookId) throws Exception {

        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (validateCheckout != null || !bookOptional.isPresent() || bookOptional.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exist or already checked out by user");
        }
        bookOptional.get().setCopiesAvailable(bookOptional.get().getCopiesAvailable() - 1);
        bookRepository.save(bookOptional.get());

        Checkout checkout = new Checkout(userEmail, LocalDate.now().toString(), LocalDate.now().plusDays(7).toString(), bookId);
        checkoutRepository.save(checkout);

    }
}
