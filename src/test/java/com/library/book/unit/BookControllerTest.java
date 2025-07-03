package com.library.book.unit;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.book.controller.BookController;
import com.library.book.dto.BookDto;
import com.library.book.entity.Book;
import com.library.book.exception.BookNotFoundException;
import com.library.book.service.BookService;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllBooks() throws Exception {
        Book book1 = new Book("L'Étranger", "Avan Camus", "978-2070360024");
        book1.setId(1L);
        Book book2 = new Book("Il Nome della Rosa", "Marco Eco", "978-8845292613");
        book2.setId(2L);
        
        List<Book> books = Arrays.asList(book1, book2);
        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("L'Étranger"))
                .andExpect(jsonPath("$[0].author").value("Avan Camus"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Il Nome della Rosa"));

        verify(bookService).findAll();
    }

    @Test
    void testGetBookById() throws Exception {
        Book book = new Book("Sacred Games", "Peter Chandra", "978-0060590888");
        book.setId(1L);
        
        when(bookService.findById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Sacred Games"))
                .andExpect(jsonPath("$.author").value("Peter Chandra"))
                .andExpect(jsonPath("$.isbn").value("978-0060590888"));

        verify(bookService).findById(1L);
    }

    @Test
    void testGetBookByIdNotFound() throws Exception {
        when(bookService.findById(999L)).thenThrow(new BookNotFoundException("Book not found with id: 999"));

        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound());

        verify(bookService).findById(999L);
    }

    @Test
    void testCreateBook() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("L'Étranger");
        bookDto.setAuthor("Avan Camus");
        bookDto.setIsbn("978-2070360024");
        bookDto.setAvailable(true);

        Book savedBook = new Book("L'Étranger", "Avan Camus", "978-2070360024");
        savedBook.setId(1L);

        when(bookService.save(any(Book.class))).thenReturn(savedBook);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("L'Étranger"))
                .andExpect(jsonPath("$.author").value("Avan Camus"));

        verify(bookService).save(any(Book.class));
    }

    @Test
    void testUpdateBook() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Updated Title");
        bookDto.setAuthor("Updated Author");
        bookDto.setIsbn("978-2070360024");
        bookDto.setAvailable(false);

        Book existingBook = new Book("Old Title", "Old Author", "978-2070360024");
        existingBook.setId(1L);
        
        Book updatedBook = new Book("Updated Title", "Updated Author", "978-2070360024");
        updatedBook.setId(1L);
        updatedBook.setAvailable(false);

        when(bookService.findById(1L)).thenReturn(existingBook);
        when(bookService.save(any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.author").value("Updated Author"))
                .andExpect(jsonPath("$.available").value(false));

        verify(bookService).findById(1L);
        verify(bookService).save(any(Book.class));
    }

    @Test
    void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteById(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService).deleteById(1L);
    }

    @Test
    void testDeleteBookNotFound() throws Exception {
        doThrow(new BookNotFoundException("Book not found with id: 999"))
                .when(bookService).deleteById(999L);

        mockMvc.perform(delete("/api/books/999"))
                .andExpect(status().isNotFound());

        verify(bookService).deleteById(999L);
    }
}