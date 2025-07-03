package com.library.book.service;

import com.library.book.entity.Book;
import java.util.List;

public interface BookService {
    
    List<Book> findAll();
    
    Book findById(Long id);
    
    Book save(Book book);
    
    void deleteById(Long id);
    
    Book findByIsbn(String isbn);
}