package com.library.backend.controllers;

import com.library.backend.dtos.BookDTO;
import com.library.backend.entities.BookEntity;
import com.library.backend.exceptions.ResourceNotFoundException;
import com.library.backend.mapper.BookMapper;
import com.library.backend.services.BookService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private BookService bookService;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookController bookController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createBook() {
        BookDTO bookDTO = new BookDTO();
        BookEntity bookEntity = new BookEntity();
        when(bookMapper.toEntity(bookDTO)).thenReturn(bookEntity);
        when(bookService.save(bookEntity)).thenReturn(bookEntity);

        BookEntity result = bookController.createBook(bookDTO);

        assertEquals(bookEntity, result);
        verify(bookService, times(1)).save(bookEntity);
    }

    @Test
    public void getAllBooks() {
        List<BookEntity> books = List.of(new BookEntity());
        when(bookService.findAll()).thenReturn(books);

        List<BookEntity> result = bookController.getAllBooks();

        assertEquals(books, result);
        verify(bookService, times(1)).findAll();
    }

    @Test
    public void getBookById() {
        BookEntity book = new BookEntity();
        when(bookService.findById(1L)).thenReturn(Optional.of(book));

        BookEntity result = bookController.getBookById("1");

        assertEquals(book, result);
        verify(bookService, times(1)).findById(1L);
    }

    @Test
    public void getBookById_NotFound() {
        when(bookService.findById(1L)).thenReturn(Optional.empty());

        thrown.expect(ResourceNotFoundException.class);
        bookController.getBookById("1");
        verify(bookService, times(1)).findById(1L);
    }

    @Test
    public void searchBooks_ByTitle() {
        List<BookEntity> books = List.of(new BookEntity());
        when(bookService.findByTitleContainingIgnoreCase("title")).thenReturn(books);

        List<BookEntity> result = bookController.searchBooks("title", null);

        assertEquals(books, result);
        verify(bookService, times(1)).findByTitleContainingIgnoreCase("title");
    }

    @Test
    public void searchBooks_ByAuthor() {
        List<BookEntity> books = List.of(new BookEntity());
        when(bookService.findByAuthorContainingIgnoreCase("author")).thenReturn(books);

        List<BookEntity> result = bookController.searchBooks(null, "author");

        assertEquals(books, result);
        verify(bookService, times(1)).findByAuthorContainingIgnoreCase("author");
    }

    @Test
    public void searchBooks_ByTitleAndAuthor() {
        List<BookEntity> books = List.of(new BookEntity());
        when(bookService.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase("title", "author")).thenReturn(books);

        List<BookEntity> result = bookController.searchBooks("title", "author");

        assertEquals(books, result);
        verify(bookService, times(1)).findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase("title", "author");
    }

    @Test
    public void updateBook() {
        BookDTO updatedBook = new BookDTO();
        BookEntity book = new BookEntity();
        BookEntity updatedEntity = new BookEntity();
        when(bookService.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toEntity(book, updatedBook)).thenReturn(updatedEntity);
        when(bookService.save(updatedEntity)).thenReturn(updatedEntity);

        BookEntity result = bookController.updateBook("1", updatedBook);

        assertEquals(updatedEntity, result);
        verify(bookService, times(1)).findById(1L);
        verify(bookService, times(1)).save(updatedEntity);
    }

    @Test
    public void updateBook_NotFound() {
        BookDTO updatedBook = new BookDTO();
        when(bookService.findById(1L)).thenReturn(Optional.empty());

        thrown.expect(ResourceNotFoundException.class);
        bookController.updateBook("1", updatedBook);
        verify(bookService, times(1)).findById(1L);
    }

    @Test
    public void deleteBook() {
        doNothing().when(bookService).deleteById(1L);

        bookController.deleteBook("1");

        verify(bookService, times(1)).deleteById(1L);
    }

}