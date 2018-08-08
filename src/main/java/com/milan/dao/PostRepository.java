package com.milan.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.milan.entity.Author;
import com.milan.entity.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

	List<Post> findAllByOrderByPostIdDesc();

	List<Post> findAllByTag(String tagId);

	List<Post> findAllByAuthor(Author author);

	Post findOneByTitle(String postTitle);  
	
	List<Post> findAllByBodyIgnoreCaseLike(String keywords);

	
}
