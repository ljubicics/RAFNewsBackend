package com.example.backend.repositories.news;

import com.example.backend.entities.Comment;
import com.example.backend.entities.News;
import com.example.backend.entities.Tag;

import java.util.List;

public interface NewsRepository {
    List<News> allNews();
    List<News> allNewsByVisits();
    News addNews(News news);
    boolean updateNews(News news, int id);
    News findNews(Integer id);
    void deleteNews(Integer id);
    List<News> allNewsByAuthor(Integer authorId);
    List<News> allNewsByCategory(Integer categoryId);
    List<News> allNewsByTag(Integer tagId);
    List<Tag> allTagByNews(Integer newsId);
    List<Comment> allCommentsByNews(Integer newsId);
    void updateViews(Integer newsId);
}
