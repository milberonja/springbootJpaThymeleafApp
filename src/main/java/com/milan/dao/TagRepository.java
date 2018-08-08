package com.milan.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.milan.entity.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {

	Tag findOneByTagId(Integer tagId);  
	
}
