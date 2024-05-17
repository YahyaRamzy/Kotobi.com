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


    @GetMapping({"/public/book"})
    public List<Book> getAllBooks(){
        return bookManagmentService.getAllBooks();
    }


    @PostMapping("/admin/book/addBook")
    public void addBook(@RequestBody Book book){
        System.out.println(book);
        bookManagmentService.insertBook(book);
    }

    @DeleteMapping("/admin/book/remove/{isbn}")
    public void removeBook(@PathVariable int isbn){
        bookManagmentService.removeBook(isbn);
    }

    @GetMapping("/public/search")
    public Optional<List<Book>> searchByBookTitleRegex(@RequestParam String title){
        return bookManagmentService.getBooksByTitleSearchRegex(title);
    }

    @GetMapping("/public/search/genre")
    public Optional<List<Book>> searchByBookGenre(@RequestParam Genre genre){
        return bookManagmentService.getBooksByGenre(genre);
    }
}
