package com.dauphine.blogger.models;

import java.util.UUID;

public class Post {
    private UUID id;
    private String title;
    private String content;
    private UUID categoryId;

    public Post(UUID id, String title, String content, UUID categoryId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
