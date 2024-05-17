package com.kotobi.app.book_managament.controller;

import com.kotobi.app.book_managament.entity.Book;
import com.kotobi.app.book_managament.service.BookManagmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookManagmentController {
    @Autowired
    BookManagmentService bookManagmentService;

    @PostMapping("/admin/addBook")
    public void addBook(@RequestBody Book book){
        System.out.println(book);
        bookManagmentService.insertBook(book);
    }
}
