package com.kotobi.app.book_managament.repository;

import com.kotobi.app.book_managament.entity.Book;
import com.kotobi.app.book_managament.entity.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findBookByISBN(int ISBN);
    void removeBookByISBN(int ISBN);
    Optional<List<Book>> findBooksByTitleRegex(String regex);
    Optional<List<Book>> findBooksByGenre(Genre genre);
    Optional<List<Book>> findBooksByAuthorRegex(String author);
    Optional<List<Book>> findBooksByLanguage(String lang);
    Optional<List<Book>> findBooksByPublisher(String publisher);


}
