package com.library.book.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.library.book.entity.Book;
import com.library.book.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class BookRepositoryIT {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testCreateBook() {
        Book book = new Book("L'Étranger", "Avan Camus", "978-2070360024");
        
        Book savedBook = bookRepository.save(book);
        
        assertNotNull(savedBook.getId());
        assertEquals("L'Étranger", savedBook.getTitle());
        assertEquals("Avan Camus", savedBook.getAuthor());
        assertEquals("978-2070360024", savedBook.getIsbn());
        assertTrue(savedBook.isAvailable());
    }

    @Test
    void testFindById() {
        Book book = new Book("Il Nome della Rosa", "Marco Eco", "978-8845292613");
        Book savedBook = bookRepository.save(book);
        
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
        
        assertTrue(foundBook.isPresent());
        assertEquals("Il Nome della Rosa", foundBook.get().getTitle());
    }

    @Test
    void testFindAll() {
        Book book1 = new Book("L'Étranger", "Avan Camus", "978-2070360024");
        Book book2 = new Book("Sacred Games", "Peter Chandra", "978-0060590888");
        
        bookRepository.save(book1);
        bookRepository.save(book2);
        
        List<Book> books = bookRepository.findAll();
        
        assertEquals(2, books.size());
    }

    @Test
    void testUpdateBook() {
        Book book = new Book("Il Nome della Rosa", "Marco Eco", "978-8845292613");
        Book savedBook = bookRepository.save(book);
        
        savedBook.setAvailable(false);
        Book updatedBook = bookRepository.save(savedBook);
        
        assertFalse(updatedBook.isAvailable());
    }

    @Test
    void testDeleteBook() {
        Book book = new Book("Sacred Games", "Peter Chandra", "978-0060590888");
        Book savedBook = bookRepository.save(book);
        
        bookRepository.deleteById(savedBook.getId());
        
        Optional<Book> deletedBook = bookRepository.findById(savedBook.getId());
        assertFalse(deletedBook.isPresent());
    }

    @Test
    void testFindByIsbn() {
        Book book = new Book("L'Étranger", "Avan Camus", "978-2070360024");
        bookRepository.save(book);
        
        Optional<Book> foundBook = bookRepository.findByIsbn("978-2070360024");
        
        assertTrue(foundBook.isPresent());
        assertEquals("L'Étranger", foundBook.get().getTitle());
    }
}