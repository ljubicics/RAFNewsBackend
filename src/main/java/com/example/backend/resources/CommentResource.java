package com.example.backend.resources;

import com.example.backend.entities.Comment;
import com.example.backend.services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comment")
public class CommentResource {
    @Inject
    private CommentService commentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> allComments() {return this.commentService.allComments();}

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Comment addComment(@Valid Comment comment) {return this.commentService.addComment(comment);}

    @GET
    @Path("/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment findComment(@PathParam("commentId") Integer id) {return this.commentService.findComment(id);}

    @DELETE
    @Path("/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteComment(@PathParam("commentId") Integer id) {return this.commentService.deleteComment(id);}

    @PATCH
    @Path("/{commentId}")
    public Comment updateComment(@Valid Comment comment, @PathParam("commentId") Integer id) {return this.commentService.updateComment(comment, id);}
}
