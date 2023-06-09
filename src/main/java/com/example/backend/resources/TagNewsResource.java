package com.example.backend.resources;

import com.example.backend.entities.News;
import com.example.backend.entities.Tag;
import com.example.backend.services.TagNewsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/tagnews")
public class TagNewsResource {
    @Inject
    private TagNewsService tagNewsService;

    @GET
    @Path("/add/{news}/{tag}")
    @Produces(MediaType.APPLICATION_JSON)
    public void addTagToNews(@PathParam("news") Integer news_id, @PathParam("tag") Integer tag_id){
        this.tagNewsService.addTagToNews(news_id, tag_id);
    }

    @GET
    @Path("/delete/{news}/{tag}")
    @Produces(MediaType.APPLICATION_JSON)
    public void removeTagFromNews(@PathParam("news") Integer news_id, @PathParam("tag") Integer tag_id){
        this.tagNewsService.removeTagFromNews(news_id, tag_id);
    }

    @GET
    @Path("/withTag/{tag}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNewsWithTag(@PathParam("tag") Integer tag){
        return this.tagNewsService.allNewsWithTag(tag);
    }

    @GET
    @Path("/tagsForNews/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tag> allTags(@PathParam("newsId") Integer newsId) {return this.tagNewsService.allTagsForNews(newsId);}

    @PATCH
    @Path("/updateTagNews/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void update(@PathParam("newsId") Integer newsId) {this.tagNewsService.update(newsId);}

    @DELETE
    @Path("/delete/{newsId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("newsId") Integer newsID) {this.tagNewsService.delete(newsID);}
}
