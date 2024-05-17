package com.kotobi.app.book_managament.service;

import com.kotobi.app.book_managament.entity.Book;
import com.kotobi.app.book_managament.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookManagmentService {
    @Autowired
    BookRepository bookRepository;

    public void insertBook(Book book){
        int bookISBN = book.getISBN();
        if(bookRepository.findBookByISBN(bookISBN).isPresent()){
            throw new IllegalStateException("Book already exists : " + bookISBN);
        }else{
            bookRepository.insert(book);
        }
    }

    public void removeBook(int ISBN){
        if(bookRepository.findBookByISBN(ISBN).isPresent()){
            bookRepository.removeBookByISBN(ISBN);
            System.out.println("Book " + ISBN + " was removed!");
        }else{
            throw new IllegalStateException("Book is not present!");
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
