package com.example.backend.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Comment {
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private Integer comment_id;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String comment_author;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String comment_text;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private long comment_date_created;

    private int comment_news;

    public Comment() {
    }

    public Comment(String comment_author, String comment_text, long comment_date_created, int comment_news) {
        this.comment_author = comment_author;
        this.comment_text = comment_text;
        this.comment_date_created = comment_date_created;
        this.comment_news = comment_news;
    }

    public Comment(Integer comment_id, String comment_author, String comment_text, long comment_date_created, int comment_news) {
        this.comment_id = comment_id;
        this.comment_author = comment_author;
        this.comment_text = comment_text;
        this.comment_date_created = comment_date_created;
        this.comment_news = comment_news;
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_author() {
        return comment_author;
    }

    public void setComment_author(String comment_author) {
        this.comment_author = comment_author;
    }

    public long getComment_date_created() {
        return comment_date_created;
    }

    public void setComment_date_created(long comment_date_created) {
        this.comment_date_created = comment_date_created;
    }

    public int getComment_news() {
        return comment_news;
    }

    public void setComment_news(int comment_news) {
        this.comment_news = comment_news;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }
}
