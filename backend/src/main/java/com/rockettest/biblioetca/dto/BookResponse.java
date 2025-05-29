// src/main/java/com/rockettest/biblioetca/dto/BookResponse.java
package com.rockettest.biblioetca.dto;

import java.util.Date;

public class BookResponse {
    private Short id;
    private String title;
    private String isbn;
    private Short publicationYear;
    private String category;
    private String imageUrl;
    private Short publisherId;
    private String publisherName;
    private Short authorId;       
    private String authorName;   
    private Date createdAt;
    private int countExcelente;
    private int countBuena;
    private int countMala;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Short getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Short publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Short getPublisherId() {
        return publisherId;
    }
    public void setPublisherId(Short publisherId) {
        this.publisherId = publisherId;
    }
    public String getPublisherName() {
        return publisherName;
    }
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Short getAuthorId() {
        return authorId;
    }
    public void setAuthorId(Short authorId) {
        this.authorId = authorId;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
     public int getCountExcelente() {
        return countExcelente;
    }
    public void setCountExcelente(int countExcelente) {
        this.countExcelente = countExcelente;
    }

    public int getCountBuena() {
        return countBuena;
    }
    public void setCountBuena(int countBuena) {
        this.countBuena = countBuena;
    }

    public int getCountMala() {
        return countMala;
    }
    public void setCountMala(int countMala) {
        this.countMala = countMala;
    }
}
