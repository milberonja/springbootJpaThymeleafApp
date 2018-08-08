package com.milan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milan.dao.PostRepository;
import com.milan.entity.Author;
import com.milan.entity.Post;

@Service
@Transactional
public class PostService {
	
	@Autowired
	private PostRepository pr;
	
	public List<Post> getAllPosts(){
		List<Post> posts = new ArrayList<>();
		for(Post post : pr.findAll()) {
			posts.add(post);
		}
	
		return posts;
	}
	
	public List<Post> getAllPostsDesc(){
		return pr.findAllByOrderByPostIdDesc();
	}

	public void saveNewPost(Post post) {
		pr.save(post);
	}
	
	public Post getPostById(String postId) {
		int id = Integer.parseInt(postId);
		Post post = pr.findById(id).get();
		return post;
	}

	public List<Post> getAllPostByTag(String tagId) {
		List<Post> posts = pr.findAllByTag(tagId);
		return posts;
	}

	public List<Post> getAllPostByAuthor(Author author) {
		List<Post> posts = pr.findAllByAuthor(author);
		return posts;
	}

	public Post getPostByTitle(String postTitle) { 
		Post post = pr.findOneByTitle(postTitle);
		return post;
	}

	public void updatePost(Post post) {
		pr.save(post);	
	}

	public void deletePost(Post post) { 
		pr.delete(post);
	}
	
	public List<Post> findByKeywords(String keywords) {
		return pr.findAllByBodyIgnoreCaseLike("%" + keywords + "%");
		
	}
	
	

	

	

	


}
