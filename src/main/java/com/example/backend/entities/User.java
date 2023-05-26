package com.example.backend.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private Integer user_id;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String user_name;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String user_last_name;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String user_email;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String user_type;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private boolean user_status;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String user_password;

    public User() {
    }

    public User(String user_name, String user_last_name, String user_email, String user_password) {
        this.user_name = user_name;
        this.user_last_name = user_last_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_type = "NORMAL_USER";
        this.user_status = true;
    }

    public User(String user_name, String user_last_name, String user_email, String user_type, boolean user_status) {
        this.user_name = user_name;
        this.user_last_name = user_last_name;
        this.user_email = user_email;
        this.user_type = user_type;
        this.user_status = user_status;
    }

    public User(String user_name, String user_last_name, String user_email, String user_type, boolean user_status, String user_password) {
        this.user_name = user_name;
        this.user_last_name = user_last_name;
        this.user_email = user_email;
        this.user_type = user_type;
        this.user_status = user_status;
        this.user_password = user_password;
    }

    public User(Integer user_id, String user_name, String user_last_name, String user_email, String user_type, boolean user_status, String user_password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_last_name = user_last_name;
        this.user_email = user_email;
        this.user_type = user_type;
        this.user_status = user_status;
        this.user_password = user_password;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public boolean isUser_status() {
        return user_status;
    }

    public void setUser_status(boolean user_status) {
        this.user_status = user_status;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
