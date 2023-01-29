package com.minvoo.bookify.service;

import com.minvoo.bookify.model.Book;
import com.minvoo.bookify.repository.BookRepository;
import com.minvoo.bookify.repository.CheckoutRepository;
import com.minvoo.bookify.repository.ReviewRepository;
import com.minvoo.bookify.requestmodels.AddBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;
    private ReviewRepository reviewRepository;
    @Autowired
    public AdminService(BookRepository bookRepository, CheckoutRepository checkoutRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
        this.reviewRepository = reviewRepository;
    }

    public void postBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setDescription(addBookRequest.getDescription());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCopies(addBookRequest.getCopies());
        book.setAuthor(addBookRequest.getAuthor());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());
        bookRepository.save(book);
    }

    public void increaseBookQuantity(Long bookId) throws Exception {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(!bookOptional.isPresent()) {
            throw new Exception("Book with given id not found");
        }
        Book book = bookOptional.get();
        book.setCopies(book.getCopies()+1);
        book.setCopiesAvailable(book.getCopiesAvailable()+1);
    }

    public void decreaseBookQuantity(Long bookId) throws Exception {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(!bookOptional.isPresent() || bookOptional.get().getCopiesAvailable() <= 0 || bookOptional.get().getCopies() <=0) {
            throw new Exception("Book book not found or quantity locked.");
        }
        Book book = bookOptional.get();
        book.setCopies(book.getCopies()-1);
        book.setCopiesAvailable(book.getCopiesAvailable()-1);
    }

    public void deleteBook(Long bookId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);

        if (!book.isPresent()) {
            throw new Exception("Book not found");
        }

        bookRepository.delete(book.get());
        checkoutRepository.deleteAllByBookId(bookId);
        reviewRepository.deleteAllByBookId(bookId);
    }
}
