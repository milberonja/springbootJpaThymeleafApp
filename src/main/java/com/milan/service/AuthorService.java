package com.milan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milan.dao.AuthorRepository;
import com.milan.entity.Author;

@Service
@Transactional
public class AuthorService {
	
	@Autowired
	AuthorRepository ar;
	
	public List<Author> getAllAuthors() {
		List<Author> authors = new ArrayList<>();
		for(Author author : ar.findAll()) {
			authors.add(author);
		}
		return authors;
	}
	
	public void createNewAuthor(Author author) {
		ar.save(author);
	}
	
	public Author chechAuthor(String userName, String password) {
		return ar.findByUserNameAndPassword(userName, password);
	}

	public Author getAuthorById(String authorId) {
		int id = Integer.parseInt(authorId);
		Author author = ar.findById(id).get();
		return author;
	}

}
