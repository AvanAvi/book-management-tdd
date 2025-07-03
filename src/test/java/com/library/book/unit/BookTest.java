package com.library.book.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.library.book.entity.Book;

class BookTest {

    @Test
    void testBookCreationWithValidData() {
        // Create book with basic  fields
        Book book = new Book("L'Étranger", "Avan Camus", "978-2070360024");
        
        assertEquals("L'Étranger", book.getTitle());
        assertEquals("Avan Camus", book.getAuthor());
        assertEquals("978-2070360024", book.getIsbn());
        assertTrue(book.isAvailable());
    }
    
    @Test
    void testBookCreationWithAllFields() {
        Book book = new Book("Il Nome della Rosa", "Marco Eco", "978-8845292613", 1980, "Fiction");
        
        assertEquals("Il Nome della Rosa", book.getTitle());
        assertEquals("Marco Eco", book.getAuthor());
        assertEquals("978-8845292613", book.getIsbn());
        assertEquals(1980, book.getPublishedYear());
        assertEquals("Fiction", book.getCategory());
        assertTrue(book.isAvailable());
    }
    
    @Test
    void testDefaultAvailabilityIsTrue() {
        Book book = new Book("Sacred Games", "Peter Chandra", "978-0060590888");
        
        assertTrue(book.isAvailable());
    }
}