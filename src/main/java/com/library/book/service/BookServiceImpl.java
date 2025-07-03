package com.library.book.service;

import org.springframework.stereotype.Service;

import com.library.book.entity.Book;
import com.library.book.repository.BookRepository;
import com.library.book.exception.BookNotFoundException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;
    
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    
    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }
    
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }
    
    @Override
    public void deleteById(Long id) {
        findById(id); 
        bookRepository.deleteById(id);
    }
    
    @Override
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
            .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));
    }
}