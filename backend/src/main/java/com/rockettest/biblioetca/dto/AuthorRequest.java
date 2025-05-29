package com.rockettest.biblioetca.dto;

import javax.validation.constraints.NotBlank;

public class AuthorRequest {

    @NotBlank(message = "El nombre del autor es obligatorio")
    private String name;

    private String biography;

    // Getters y Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }
    public void setBiography(String biography) {
        this.biography = biography;
    }
}