package com.example.backend.repositories.tag;

import com.example.backend.entities.Tag;

import java.util.List;

public interface TagRepository {
    List<Tag> allTags();
    Tag addTag(Tag tag);
    Tag findTag(Integer id);
    boolean deleteTag(Integer id);
}
