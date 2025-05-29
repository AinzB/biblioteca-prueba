package com.rockettest.biblioetca.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class BookLoanRequest {

    @NotNull(message = "El ID de la copia de libro es obligatorio")
    private Short bookCopyId;

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Short studentId;

    @NotNull(message = "La fecha de devolución prevista es obligatoria")
    private Date dueDate;

    /** Fecha real de devolución (opcional) */
    private Date returnDate;

    public Short getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(Short bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public Short getStudentId() {
        return studentId;
    }

    public void setStudentId(Short studentId) {
        this.studentId = studentId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
