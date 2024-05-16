package com.kotobi.app.book_managament.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Document
public class Book {

    @Id
    private String id;
    private String title;
    private String author;
    @Indexed(unique = true)
    private int ISBN;
    private String publisher;
    private Date publish_date;
    private Genre genre;
    private int page_count;
    private String language;
    private String cover_image;
    private String summary;



}
