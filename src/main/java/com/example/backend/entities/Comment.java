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
    private long comment_date_created;

    private News comment_news;

    public Comment() {
    }

    public Comment(String comment_author, long comment_date_created, News comment_news) {
        this.comment_author = comment_author;
        this.comment_date_created = comment_date_created;
        this.comment_news = comment_news;
    }

    public Comment(Integer comment_id, String comment_author, long comment_date_created, News comment_news) {
        this.comment_id = comment_id;
        this.comment_author = comment_author;
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

    public News getComment_news() {
        return comment_news;
    }

    public void setComment_news(News comment_news) {
        this.comment_news = comment_news;
    }
}
