package com.example.backend.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Tag {

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private Integer tag_id;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String tag_keyword;

    public Tag() {
    }

    public Tag(String tag_keyword) {
        this.tag_keyword = tag_keyword;
    }

    public Tag(Integer tag_id, String tag_keyword) {
        this.tag_id = tag_id;
        this.tag_keyword = tag_keyword;
    }

    public Integer getTag_id() {
        return tag_id;
    }

    public void setTag_id(Integer tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_keyword() {
        return tag_keyword;
    }

    public void setTag_keyword(String tag_keyword) {
        this.tag_keyword = tag_keyword;
    }
}
