package com.rockettest.biblioetca.dto.response;

import java.util.Date;

public class BookCopyResponse {
    private Short id;
    private Short bookId;
    private String bookTitle;
    private String bookImageUrl;
    private Short publisherId;
    private String publisherName;
    private Short authorId;  
    private String authorName;
    private String status;
    private String condition;
    private Date createdAt;

    // Getters y setters...

    public Short getId() {
        return id;
    }
    public void setId(Short id) {
        this.id = id;
    }

    public Short getBookId() {
        return bookId;
    }
    public void setBookId(Short bookId) {
        this.bookId = bookId;
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

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
}
