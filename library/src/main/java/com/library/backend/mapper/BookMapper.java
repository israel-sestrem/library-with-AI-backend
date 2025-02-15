package com.library.backend.mapper;

import com.library.backend.dtos.BookDTO;
import com.library.backend.entities.BookEntity;

public class BookMapper {

    public BookEntity toEntity(BookDTO book) {
        var bookEntity = new BookEntity();
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setIsbn(book.getIsbn());
        bookEntity.setPublicationYear(book.getPublicationYear());
        bookEntity.setDescription(book.getDescription());
        return bookEntity;
    }

    public BookEntity toEntity(BookEntity book, BookDTO updatedBook) {
        if(updatedBook.getTitle() != null && !updatedBook.getTitle().isEmpty()) {
            book.setTitle(updatedBook.getTitle());
        }
        if(updatedBook.getAuthor() != null && !updatedBook.getAuthor().isEmpty()) {
            book.setAuthor(updatedBook.getAuthor());
        }
        if(updatedBook.getIsbn() != null && !updatedBook.getIsbn().isEmpty()) {
            book.setIsbn(updatedBook.getIsbn());
        }
        if(updatedBook.getPublicationYear() != 0) {
            book.setPublicationYear(updatedBook.getPublicationYear());
        }
        if(updatedBook.getDescription() != null && !updatedBook.getDescription().isEmpty()) {
            book.setDescription(updatedBook.getDescription());
        }

        return book;
    }

}
