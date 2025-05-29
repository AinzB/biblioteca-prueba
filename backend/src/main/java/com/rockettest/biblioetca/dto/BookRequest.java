// src/main/java/com/rockettest/biblioetca/dto/BookRequest.java
package com.rockettest.biblioetca.dto;

import javax.validation.constraints.*;

public class BookRequest {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "El ISBN es obligatorio")
    private String isbn;

    @NotNull(message = "El año de publicación es obligatorio")
    @Min(value = 1500, message = "El año debe ser al menos 1500")
    @Max(value = 2025, message = "El año no puede superar el año actual")
    private Short publicationYear;

    @NotBlank(message = "La categoría es obligatoria")
    private String category;

    @Size(max = 200, message = "La URL no puede exceder 200 caracteres")
    private String imageUrl;

    @NotNull(message = "El ID de la editorial es obligatorio")
    private Short publisherId;

    @NotNull(message = "El ID del autor es obligatorio")
    private Short authorId;

    // Getters y Setters

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

    public Short getAuthorId() {
        return authorId;
    }
    public void setAuthorId(Short authorId) {
        this.authorId = authorId;
    }
}
