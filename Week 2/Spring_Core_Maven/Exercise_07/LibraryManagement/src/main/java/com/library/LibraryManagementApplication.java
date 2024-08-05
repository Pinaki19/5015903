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

        // Get the BookService bean configured with constructor injection
        BookService bookServiceConstructor = (BookService) context.getBean("bookServiceConstructor");
        if (bookServiceConstructor != null) {
            System.out.println("BookService bean (constructor injection) created successfully.");
        } else {
            System.out.println("Failed to create BookService bean (constructor injection).");
        }

        // Get the BookService bean configured with setter injection
        BookService bookServiceSetter = (BookService) context.getBean("bookServiceSetter");
        if (bookServiceSetter != null) {
            System.out.println("BookService bean (setter injection) created successfully.");
        } else {
            System.out.println("Failed to create BookService bean (setter injection).");
        }
    }
}
