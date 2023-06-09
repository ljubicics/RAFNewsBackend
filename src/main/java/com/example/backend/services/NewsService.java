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

    public News findNews(int id) {
        return this.newsRepository.findNews(id);
    }

    public void deleteNews(int id) {this.newsRepository.deleteNews(id);}

    public boolean updateNews(News news, int id) {return this.newsRepository.updateNews(news, id);}

    public List<News> allNewsByAuthor(int id) {return this.newsRepository.allNewsByAuthor(id);}

    public List<News> allNewsByCategory(int id) {return this.newsRepository.allNewsByCategory(id);}
    public List<News> popularNews(){ return this.newsRepository.allNewsByVisits(); }
    public void updateViews(Integer newsId) {this.newsRepository.updateViews(newsId);}
}
