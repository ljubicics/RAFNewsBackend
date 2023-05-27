package com.example.backend.resources;

import com.example.backend.entities.News;
import com.example.backend.entities.User;
import com.example.backend.services.NewsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/news")
public class NewsResource {
    @Inject
    private NewsService newsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> all()
    {
        return this.newsService.allNews();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public News addUser(@Valid News news) {
        return this.newsService.addNews(news);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News findNews(@PathParam("id") int id) {
        return this.newsService.findNews(id);
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") int id) {
        this.newsService.deleteNews(id);
    }

    @PATCH
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean update(@Valid News news, @PathParam("id") int id) {return this.newsService.updateNews(news, id);}

    @GET
    @Path("allNewsByAuthor/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNewsByAuthor(@PathParam("id") int id) {return this.newsService.allNewsByAuthor(id);}

    @GET
    @Path("allNewsByCategory/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> allNewsByCategory(@PathParam("id") int id) {return this.newsService.allNewsByCategory(id);}
}
