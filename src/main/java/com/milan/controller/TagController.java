package com.milan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.milan.entity.Tag;
import com.milan.service.TagService;

@Controller
@RequestMapping("/tags")
public class TagController {
	
	@Autowired
	private TagService ts;
	
	@RequestMapping({"/", ""})
	public List<Tag> getAllTags(){
		return ts.getAllTags();
	}
	
	@RequestMapping("/createNew")
	public String createNewTag(){
		Tag tag = new Tag();
		tag.setTagName("Diving");
		ts.putNewTag(tag);
		
		return "redirect:../";
	}

}
