package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.repository.BookRepository;
import com.library.service.BookService;

public class LibraryManagementApplication
{
    public static void main( String[] args )
    {
        System.out.println( "\nHello World! from Spring" );
        
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bs = (BookService) context.getBean("bookService");
        bs.bookServiceShow();
        BookRepository br = (BookRepository) context.getBean("bookRepository");
        br.bookRepositoryShow();
    }
}
