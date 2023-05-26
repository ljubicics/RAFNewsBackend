package com.example.backend.repositories.news;

import com.example.backend.entities.Comment;
import com.example.backend.entities.News;
import com.example.backend.entities.Tag;

import java.util.List;

public interface NewsRepository {
    List<News> allNews();
    List<News> allNewsByVisits();
    News addNews(News news);
    News updateNews(News news);
    News findNews(Integer id);
    void deleteNews(Integer id);
    List<News> allByAuthor(Integer authorId);
    List<News> allByCategory(Integer categoryId);
    List<News> allByTag(Integer tagId);
    List<Tag> allTagByNews(Integer newsId);
    List<Comment> allCommentsByNews(Integer newsId);
}
