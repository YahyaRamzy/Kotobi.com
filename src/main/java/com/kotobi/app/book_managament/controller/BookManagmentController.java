package com.kotobi.app.book_managament.controller;

import com.kotobi.app.book_managament.entity.Book;
import com.kotobi.app.book_managament.entity.Genre;
import com.kotobi.app.book_managament.service.BookManagmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookManagmentController {
    @Autowired
    BookManagmentService bookManagmentService;


    @PostMapping("/admin/book/addBook")
    public void addBook(@RequestBody Book book){
        System.out.println(book);
        bookManagmentService.insertBook(book);
    }

    @DeleteMapping("/admin/book/remove/{isbn}")
    public void removeBook(@PathVariable int isbn){
        bookManagmentService.removeBook(isbn);
    }


}
