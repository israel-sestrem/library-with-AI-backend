package com.library.backend.controllers;

import com.library.backend.dtos.BookDTO;
import com.library.backend.entities.BookEntity;
import com.library.backend.exceptions.ResourceNotFoundException;
import com.library.backend.mapper.BookMapper;
import com.library.backend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
class BookController {
    private static final String AI_URL = "URL_TO_AI_API";

    private final BookService bookService;
    private final RestTemplate restTemplate;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
        this.restTemplate = new RestTemplate();
        this.bookMapper = new BookMapper();
    }

    @PostMapping
    public BookEntity createBook(@RequestBody BookDTO book) {
        return bookService.save(bookMapper.toEntity(book));
    }

    @GetMapping
    public List<BookEntity> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookEntity getBookById(@PathVariable String id) {
        return bookService.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    @GetMapping("/search")
    public List<BookEntity> searchBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author) {
        boolean containsTitle = title != null && !title.isEmpty();
        boolean containsAuthor = author != null && !author.isEmpty();

        if (containsTitle && containsAuthor) {
            return bookService.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
        }

        if (containsTitle) {
            return bookService.findByTitleContainingIgnoreCase(title);
        }

        if (containsAuthor) {
            return bookService.findByAuthorContainingIgnoreCase(author);
        }

        return List.of();
    }

    @PutMapping("/{id}")
    public BookEntity updateBook(@PathVariable String id, @RequestBody BookDTO updatedBook) {
        var book = bookService.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        return bookService.save(bookMapper.toEntity(book, updatedBook));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        bookService.deleteById(Long.valueOf(id));
    }

    // obs: This is just a placeholder implementation.
    // I couldn't find any public AI API :(.
    @GetMapping("/{id}/ai-insights")
    public BookEntity getBookInsights(@PathVariable String id) {
        BookEntity book = bookService.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        if (book.getAiInsights() == null || book.getAiInsights().isEmpty()) {
            String prompt = "Generate insights about the book " + book.getTitle();
            Map<String, Object> requestBody = Map.of();

            ResponseEntity<Map> response = restTemplate.postForEntity(AI_URL, requestBody, Map.class);
            String aiResponse = response.getBody().get("response").toString();

            book.setAiInsights(aiResponse);
            bookService.save(book);
        }

        return book;
    }
}
