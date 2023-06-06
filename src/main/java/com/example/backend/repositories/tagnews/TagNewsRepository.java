package com.example.backend.repositories.tagnews;

import com.example.backend.entities.News;

import java.util.List;

public interface TagNewsRepository {
    public void addTagToNews(Integer news_id, Integer tag_id);
    public void removeTagFromNews(Integer news_id, Integer tag_id);
    public List<News> findNewsWithTag(Integer tag_id);

}
