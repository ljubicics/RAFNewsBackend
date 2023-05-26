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
}
