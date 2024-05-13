package com.kotobi.app.book_managament.repository;

import com.kotobi.app.book_managament.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface BookRepository extends MongoRepository<Book, UUID> {



}
