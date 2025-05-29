package com.rockettest.biblioetca.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class BookCopyRequest {

    @NotNull(message = "El ID del libro es obligatorio")
    private Short bookId;

    @NotNull(message = "El ID de la editorial es obligatorio")
    private Short publisherId;

    @NotNull(message = "El estado (status) es obligatorio")
    @Pattern(
      regexp = "DISPONIBLE|PRESTADO|PERDIDO",
      message = "El status debe ser DISPONIBLE, PRESTADO o PERDIDO"
    )
    private String status;

    @NotNull(message = "La condición es obligatoria")
    @Pattern(
      regexp = "EXCELENTE|BUENA|MALA",
      message = "La condición debe ser EXCELENTE, BUENA o MALA"
    )
    private String condition;

    // Getters y Setters

    public Short getBookId() {
        return bookId;
    }

    public void setBookId(Short bookId) {
        this.bookId = bookId;
    }

    public Short getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Short publisherId) {
        this.publisherId = publisherId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}