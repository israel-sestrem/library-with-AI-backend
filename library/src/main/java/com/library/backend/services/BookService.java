package com.library.backend.services;

import com.library.backend.entities.BookEntity;
import com.library.backend.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public BookEntity save(BookEntity entity) {
        return bookRepository.save(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<BookEntity> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<BookEntity> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<BookEntity> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(String title, String author) {
        return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
    }

    @Transactional(readOnly = true)
    public List<BookEntity> findByTitleContainingIgnoreCase(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Transactional(readOnly = true)
    public List<BookEntity> findByAuthorContainingIgnoreCase(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

}
