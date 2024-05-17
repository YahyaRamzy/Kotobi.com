package com.kotobi.app.book_managament.service;

import com.kotobi.app.book_managament.entity.Book;
import com.kotobi.app.book_managament.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
