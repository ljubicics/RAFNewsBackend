package com.example.backend.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class News {
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private Integer news_id;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String news_title;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String news_text;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private long news_date_created;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private int news_views;
    private User news_author;
    private Category news_category;

    public News() {
    }

    public News(Integer news_id, String news_title, String news_text, long news_date_created, User news_author, Category news_category) {
        this.news_id = news_id;
        this.news_title = news_title;
        this.news_text = news_text;
        this.news_date_created = news_date_created;
        this.news_author = news_author;
        this.news_category = news_category;
        this.news_views = 0;
    }

    public News(String news_title, String news_text, long news_date_created, int news_views, User news_author, Category news_category) {
        this.news_title = news_title;
        this.news_text = news_text;
        this.news_date_created = news_date_created;
        this.news_views = news_views;
        this.news_author = news_author;
        this.news_category = news_category;
    }

    public News(Integer news_id, String news_title, String news_text, long news_date_created, int news_views, User news_author, Category news_category) {
        this.news_id = news_id;
        this.news_title = news_title;
        this.news_text = news_text;
        this.news_date_created = news_date_created;
        this.news_views = news_views;
        this.news_author = news_author;
        this.news_category = news_category;
    }

    public Integer getNews_id() {
        return news_id;
    }

    public void setNews_id(Integer news_id) {
        this.news_id = news_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_text() {
        return news_text;
    }

    public void setNews_text(String news_text) {
        this.news_text = news_text;
    }

    public long getNews_date_created() {
        return news_date_created;
    }

    public void setNews_date_created(long news_date_created) {
        this.news_date_created = news_date_created;
    }

    public int getNews_views() {
        return news_views;
    }

    public void setNews_views(int news_views) {
        this.news_views = news_views;
    }

    public User getNews_author() {
        return news_author;
    }

    public void setNews_author(User news_author) {
        this.news_author = news_author;
    }

    public Category getNews_category() {
        return news_category;
    }

    public void setNews_category(Category news_category) {
        this.news_category = news_category;
    }
}
