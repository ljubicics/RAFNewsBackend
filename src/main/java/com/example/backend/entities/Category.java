package com.example.backend.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Category {
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private Integer category_id;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String category_name;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String category_description;

    public Category() {
    }

    public Category(String category_name, String category_description) {
        this.category_name = category_name;
        this.category_description = category_description;
    }

    public Category(Integer category_id, String category_name, String category_description) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_description = category_description;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }
}
