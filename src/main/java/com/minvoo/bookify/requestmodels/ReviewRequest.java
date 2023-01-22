package com.minvoo.bookify.requestmodels;

import java.util.Optional;

public class ReviewRequest {

    private double rating;
    private  Long bookId;
    private Optional<String> reviewDescription;

}
