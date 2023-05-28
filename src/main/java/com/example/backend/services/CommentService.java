package com.example.backend.services;

import com.example.backend.entities.Comment;
import com.example.backend.repositories.comment.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {
    @Inject
    private CommentRepository commentRepository;

    public List<Comment> allComments() {return this.commentRepository.allComments();}

    public Comment addComment(Comment comment) {return this.commentRepository.addComment(comment);}

    public Comment findComment(Integer commentId) {return this.commentRepository.findComment(commentId);}

    public boolean deleteComment(Integer commentId) {return this.commentRepository.deleteComment(commentId);}

    public Comment updateComment(Comment comment, Integer commentId) {return this.commentRepository.updateComment(comment, commentId);}
}
