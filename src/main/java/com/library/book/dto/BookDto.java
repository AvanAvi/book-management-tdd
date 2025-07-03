package com.library.book.dto;

import jakarta.validation.constraints.NotBlank;

public class BookDto {
    
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Author is required")
    private String author;
    
    @NotBlank(message = "ISBN is required")
    private String isbn;
    
    private Integer publishedYear;
    private String category;
    private Boolean available = true;
    
    // Default constructor
    public BookDto() {}
    
    // Constructor with required fields
    public BookDto(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
    }
    
    // Constructor with all fields
    public BookDto(Long id, String title, String author, String isbn, Integer publishedYear, String category, Boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.category = category;
        this.available = available;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public Integer getPublishedYear() {
        return publishedYear;
    }
    
    public String getCategory() {
        return category;
    }
    
    public Boolean getAvailable() {
        return available;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setAvailable(Boolean available) {
        this.available = available;
    }
}