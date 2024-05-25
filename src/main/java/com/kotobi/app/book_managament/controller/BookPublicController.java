package com.kotobi.app.book_managament.controller;

import com.kotobi.app.book_managament.entity.Book;
import com.kotobi.app.book_managament.entity.Genre;
import com.kotobi.app.book_managament.service.BookManagmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public/book")
public class BookPublicController {

    @Autowired
    BookManagmentService bookManagmentService;
    @GetMapping({"/"})
    public List<Book> getAllBooks(){
        return bookManagmentService.getAllBooks();
    }

    @GetMapping("/search/title")
    public Optional<List<Book>> searchByBookTitleRegex(@RequestParam String title){
        return bookManagmentService.getBooksByTitleSearchRegex(title);
    }

    @GetMapping("/search/genre")
    public Optional<List<Book>> searchByBookGenre(@RequestParam Genre genre){
        return bookManagmentService.getBooksByGenre(genre);
    }
}
