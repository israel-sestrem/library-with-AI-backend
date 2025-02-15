package com.library.backend.dtos;

public class BookDTO {

    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private String description;

    public BookDTO() {
    }

    public BookDTO(String title, String author, String isbn, int publicationYear, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getDescription() {
        return description;
    }
}
