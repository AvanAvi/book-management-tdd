package com.library.book.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.library.book.entity.Book;
import com.library.book.repository.BookRepository;
import com.library.book.service.BookService;
import com.library.book.service.BookServiceImpl;
import com.library.book.exception.BookNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    void testFindAllBooks() {
        Book book1 = new Book("L'Étranger", "Avan Camus", "978-2070360024");
        Book book2 = new Book("Il Nome della Rosa", "Marco Eco", "978-8845292613");
        List<Book> expectedBooks = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.findAll();

        assertEquals(2, actualBooks.size());
        assertEquals(expectedBooks, actualBooks);
        verify(bookRepository).findAll();
    }

    @Test
    void testFindByIdExistingBook() {
        Book testBook = new Book("Sacred Games", "Peter Chandra", "978-0060590888");
        testBook.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        Book foundBook = bookService.findById(1L);

        assertEquals(testBook, foundBook);
        verify(bookRepository).findById(1L);
    }

    @Test
    void testFindByIdNonExistentBook() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.findById(999L));
        verify(bookRepository).findById(999L);
    }

    @Test
    void testSaveBook() {
        Book bookToSave = new Book("Sacred Games", "Peter Chandra", "978-0060590888");
        Book savedBook = new Book("Sacred Games", "Peter Chandra", "978-0060590888");
        savedBook.setId(2L);

        when(bookRepository.save(bookToSave)).thenReturn(savedBook);

        Book result = bookService.save(bookToSave);

        assertEquals(savedBook, result);
        verify(bookRepository).save(bookToSave);
    }

    @Test
    void testDeleteById() {
        Book testBook = new Book("L'Étranger", "Avan Camus", "978-2070360024");
        testBook.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        bookService.deleteById(1L);

        verify(bookRepository).findById(1L);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void testDeleteByIdNonExistentBook() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteById(999L));
        verify(bookRepository).findById(999L);
        verify(bookRepository, never()).deleteById(999L);
    }

    @Test
    void testFindByIsbn() {
        Book testBook = new Book("L'Étranger", "Avan Camus", "978-2070360024");

        when(bookRepository.findByIsbn("978-2070360024")).thenReturn(Optional.of(testBook));

        Book foundBook = bookService.findByIsbn("978-2070360024");

        assertEquals(testBook, foundBook);
        verify(bookRepository).findByIsbn("978-2070360024");
    }

    @Test
    void testFindByIsbnNotFound() {
        when(bookRepository.findByIsbn("999999999")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.findByIsbn("999999999"));
        verify(bookRepository).findByIsbn("999999999");
    }
}