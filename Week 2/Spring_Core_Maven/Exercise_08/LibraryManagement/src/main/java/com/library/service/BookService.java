package com.library.service;

import com.library.repository.BookRepository;
public class BookService {
    private BookRepository bookRepository;

    // Constructor for dependency injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Setter for dependency injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

}
