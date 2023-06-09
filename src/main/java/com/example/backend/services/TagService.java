package com.example.backend.services;

import com.example.backend.entities.Category;
import com.example.backend.entities.Tag;
import com.example.backend.repositories.tag.TagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {
    @Inject
    private TagRepository tagRepository;

    public List<Tag> allTags() {return this.tagRepository.allTags();}

    public Tag addTag(Tag tag) {return this.tagRepository.addTag(tag);}

    public boolean deleteTag(Integer id) {return this.tagRepository.deleteTag(id);}

    public Tag findTag(Integer id) {return this.tagRepository.findTag(id);}

    public Tag updateTag(int tagId, Tag tag) {return this.tagRepository.updateTag(tagId, tag);}

}
