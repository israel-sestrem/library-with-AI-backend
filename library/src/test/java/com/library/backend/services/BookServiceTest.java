package com.library.backend.services;

import com.library.backend.entities.BookEntity;
import com.library.backend.repositories.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveBook() {
        BookEntity bookEntity = new BookEntity();
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);

        BookEntity result = bookService.save(bookEntity);

        assertEquals(bookEntity, result);
        verify(bookRepository, times(1)).save(bookEntity);
    }

    @Test
    public void deleteBookById() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteById(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    public void findAllBooks() {
        List<BookEntity> books = List.of(new BookEntity());
        when(bookRepository.findAll()).thenReturn(books);

        List<BookEntity> result = bookService.findAll();

        assertEquals(books, result);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void findBookById() {
        BookEntity book = new BookEntity();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<BookEntity> result = bookService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void findBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<BookEntity> result = bookService.findById(1L);

        assertFalse(result.isPresent());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void findBooksByTitleAndAuthor() {
        List<BookEntity> books = List.of(new BookEntity());
        when(bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase("title", "author")).thenReturn(books);

        List<BookEntity> result = bookService.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase("title", "author");

        assertEquals(books, result);
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase("title", "author");
    }

    @Test
    public void findBooksByTitle() {
        List<BookEntity> books = List.of(new BookEntity());
        when(bookRepository.findByTitleContainingIgnoreCase("title")).thenReturn(books);

        List<BookEntity> result = bookService.findByTitleContainingIgnoreCase("title");

        assertEquals(books, result);
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("title");
    }

    @Test
    public void findBooksByAuthor() {
        List<BookEntity> books = List.of(new BookEntity());
        when(bookRepository.findByAuthorContainingIgnoreCase("author")).thenReturn(books);

        List<BookEntity> result = bookService.findByAuthorContainingIgnoreCase("author");

        assertEquals(books, result);
        verify(bookRepository, times(1)).findByAuthorContainingIgnoreCase("author");
    }

}