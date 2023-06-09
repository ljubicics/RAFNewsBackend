package com.example.backend.services;

import com.example.backend.entities.News;
import com.example.backend.entities.Tag;
import com.example.backend.repositories.tagnews.TagNewsRepository;

import javax.inject.Inject;
import java.util.List;

public class TagNewsService {
    @Inject
    TagNewsRepository tagNewsRepository;

    public void addTagToNews(Integer news_id, Integer tag_id){
        this.tagNewsRepository.addTagToNews(news_id, tag_id);
    }
    public void removeTagFromNews(Integer news_id, Integer tag_id){
        this.tagNewsRepository.removeTagFromNews(news_id, tag_id);
    }
    public List<News> allNewsWithTag(Integer tag_id){
        return this.tagNewsRepository.findNewsWithTag(tag_id);
    }
    public List<Tag> allTagsForNews(Integer newsId) {return this.tagNewsRepository.allTagsForNews(newsId);}
    public void update(Integer newsId) {this.tagNewsRepository.update(newsId);}
    public void delete(Integer newsId) {this.tagNewsRepository.delete(newsId);}
}
