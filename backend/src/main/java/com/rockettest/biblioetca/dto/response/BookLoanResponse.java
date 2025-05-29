package com.rockettest.biblioetca.dto.response;

import java.util.Date;

public class BookLoanResponse {
    private Short id;
    private Short bookCopyId;
    private Short studentId;
    private Date loanDate;
    private Date dueDate;
    private Date returnDate;

    // Campos adicionales para display
    private String bookTitle;
    private String bookImageUrl;
    private String authorName;
    private String publisherName;
    private String status;
    private String condition;
    private String studentName;

    public Short getId() {
        return id;
    }
    public void setId(Short id) {
        this.id = id;
    }

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

    public Date getLoanDate() {
        return loanDate;
    }
    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
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

    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }
    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublisherName() {
        return publisherName;
    }
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
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

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
