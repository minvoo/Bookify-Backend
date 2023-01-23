package com.minvoo.bookify.controller;

import com.minvoo.bookify.model.Book;
import com.minvoo.bookify.responsemodels.ShelfCurrentLoansResponse;
import com.minvoo.bookify.service.BookService;
import com.minvoo.bookify.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader(value = "Authorization") String token,
                             @RequestParam Long bookId) throws Exception {
        String userEmail = "testuser@mail.com";
        return bookService.checkoutBook(userEmail, bookId);
    }

    @GetMapping("/secure/currentLoans")
    public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token) throws Exception {
      String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
      return bookService.currentLoans(userEmail);
    }
    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token,
                                      @RequestParam Long bookId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = "testuser@mail.com";
        return bookService.currentLoansCount(userEmail);
    }
}
