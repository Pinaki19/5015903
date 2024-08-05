package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class LibraryManagementApplication 
{
    public static void main( String[] args )
    {
        System.out.println( "\nHello World! from Spring" );
        
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bs = (BookService) context.getBean("bookService");
        bs.bookServiceShow();
    }
}
