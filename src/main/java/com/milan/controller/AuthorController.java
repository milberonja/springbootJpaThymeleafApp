package com.milan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.milan.entity.Author;
import com.milan.service.AuthorService;

@Controller
@RequestMapping("/authors")
public class AuthorController {
	
	@Autowired
	private AuthorService as;
	
	@RequestMapping({"/",""})
	public List<Author> getAllAuthors() {
		
		return as.getAllAuthors();
		
	}
	
	@RequestMapping("/manage")
	public String menagePost() {
		
		return "views/managePost";
		
	}
	
	@RequestMapping("/createNew")
	public String createNewAuthor() {
		return "";
	}

}
