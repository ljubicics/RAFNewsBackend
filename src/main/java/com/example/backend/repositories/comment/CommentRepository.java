package com.example.backend.repositories.comment;

import com.example.backend.entities.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> allComments();
    Comment addComment(Comment comment);
    Comment findComment(Integer id);
    boolean deleteComment(Integer id);
    Comment updateComment(Comment comment, Integer id);
}
