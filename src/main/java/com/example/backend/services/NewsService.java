package com.example.backend.services;

import com.example.backend.entities.News;
import com.example.backend.repositories.news.NewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {

    @Inject
    private NewsRepository newsRepository;

    public List<News> allNews() {
        return this.newsRepository.allNews();
    }

    public News addNews(News news) {return this.newsRepository.addNews(news);}

}
