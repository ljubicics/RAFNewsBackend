package com.example.backend.resources;

import com.example.backend.entities.Tag;
import com.example.backend.services.TagService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/tag")
public class TagResource {
    @Inject
    private TagService tagService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> allTags() {return this.tagService.allTags();}

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Tag addTag(@Valid Tag tag) {return this.tagService.addTag(tag);}

    @DELETE
    @Path("/{tagId}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteTag(@PathParam("tagId") Integer tagId) {return this.tagService.deleteTag(tagId);}

    @GET
    @Path("/{tagId}")
    @Produces(MediaType.APPLICATION_JSON)
    public  Tag findTag(@PathParam("tagId") Integer tagId) {return this.tagService.findTag(tagId);}
}
