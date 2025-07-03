package com.library.book.unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.library.book.dto.BookDto;

class BookDtoTest {

    @Test
    void testBookDtoCreationWithValidData() {
        BookDto bookDto = new BookDto("L'Étranger", "Avan Camus", "978-2070360024");
        
        assertEquals("L'Étranger", bookDto.getTitle());
        assertEquals("Avan Camus", bookDto.getAuthor());
        assertEquals("978-2070360024", bookDto.getIsbn());
        assertTrue(bookDto.getAvailable());
    }
    
    @Test
    void testBookDtoCreationWithAllFields() {
        BookDto bookDto = new BookDto(1L, "Il Nome della Rosa", "Marco Eco", "978-8845292613", 1980, "Fiction", true);
        
        assertEquals(1L, bookDto.getId());
        assertEquals("Il Nome della Rosa", bookDto.getTitle());
        assertEquals("Marco Eco", bookDto.getAuthor());
        assertEquals("978-8845292613", bookDto.getIsbn());
        assertEquals(1980, bookDto.getPublishedYear());
        assertEquals("Fiction", bookDto.getCategory());
        assertTrue(bookDto.getAvailable());
    }
    
    @Test
    void testDefaultConstructor() {
        BookDto bookDto = new BookDto();
        
        assertNull(bookDto.getTitle());
        assertNull(bookDto.getAuthor());
        assertNull(bookDto.getIsbn());
    }
}