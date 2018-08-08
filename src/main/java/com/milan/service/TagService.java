package com.milan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milan.dao.TagRepository;
import com.milan.entity.Tag;

@Service
@Transactional
public class TagService {
	
	@Autowired
	private TagRepository tr;
	
	public List<Tag> getAllTags(){
		List<Tag> tags = new ArrayList<>();
		for(Tag tag : tr.findAll()) {
			tags.add(tag);
		}
		return tags;
	}
	
	public void putNewTag(Tag tag) {
		tr.save(tag);
	}

	public Tag getTagByTagId(String tagName) {
		int tagId = Integer.parseInt(tagName);
		Tag tag = tr.findOneByTagId(tagId);
		return tag;
	}

}
