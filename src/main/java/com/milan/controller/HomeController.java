package com.milan.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.milan.entity.Author;
import com.milan.entity.Post;
import com.milan.service.AuthorService;
import com.milan.service.PostService;
import com.milan.service.TagService;

@Controller
public class HomeController {
	
	@Autowired
	private AuthorService as;
	
	@Autowired
	private TagService ts;
	
	@Autowired
	private PostService ps;
	
	@RequestMapping({"","/"})
	public String getIndexPage(Model model) {
		
		List<Post> allPosts = ps.getAllPostsDesc(); 
		List<Post> sortedPosts = new ArrayList<>();
		List<Post> favouritePosts = new ArrayList<>();
		int maxNum = 0;
		
		Map<Post, Integer> posts = new HashMap<>();
		for(Post post : allPosts) {
			int size = post.getComments().size();
			posts.put(post, size);
			if(size > maxNum) {
				maxNum = size;
			}
		}
		for(int i=maxNum; i>0; i--) {
			for(Entry<Post, Integer> entry : posts.entrySet()) {
				if(entry.getValue() == i) {
					sortedPosts.add(entry.getKey());
				}
			}
		}
		for(int i=0; i<3; i++) {
			favouritePosts.add(sortedPosts.get(i));
		}
		
		
		model.addAttribute("authors", as.getAllAuthors());
		model.addAttribute("tags", ts.getAllTags());
		model.addAttribute("posts", allPosts);	
		model.addAttribute("favouritePosts", favouritePosts);

		return "index";
	}
	
	@RequestMapping("/home")
	public String getHomePage(@RequestParam("error") String error, Model model) {
		
		List<Post> allPosts = ps.getAllPostsDesc(); 
		List<Post> sortedPosts = new ArrayList<>();
		List<Post> favouritePosts = new ArrayList<>();
		int maxNum = 0;
		
		Map<Post, Integer> posts = new HashMap<>();
		for(Post post : allPosts) {
			int size = post.getComments().size();
			posts.put(post, size);
			if(size > maxNum) {
				maxNum = size;
			}
		}
		for(int i=maxNum; i>0; i--) {
			for(Entry<Post, Integer> entry : posts.entrySet()) {
				if(entry.getValue() == i) {
					sortedPosts.add(entry.getKey());
				}
			}
		}
		for(int i=0; i<3; i++) {
			favouritePosts.add(sortedPosts.get(i));
		}
		
		
		model.addAttribute("authors", as.getAllAuthors());
		model.addAttribute("tags", ts.getAllTags());
		model.addAttribute("favouritePosts", favouritePosts);
		model.addAttribute("posts", allPosts);	
		model.addAttribute("error", error);
		
		return "index";
	}
	
	@RequestMapping("/search/{keywords}")
	public String showSearchResult(@PathVariable String keywords,RedirectAttributes ra, Model model) {
		
		List<Post> searchResults = ps.findByKeywords(keywords);
		if(searchResults.isEmpty()) {
			ra.addAttribute("error", "NO_SEARCH_RESULTS");
			return "redirect:/home";
		} else {
			List<Post> allPosts = ps.getAllPostsDesc(); 
			List<Post> sortedPosts = new ArrayList<>();
			List<Post> favouritePosts = new ArrayList<>();
			int maxNum = 0;
			
			Map<Post, Integer> posts = new HashMap<>();
			for(Post post : allPosts) {
				int size = post.getComments().size();
				posts.put(post, size);
				if(size > maxNum) {
					maxNum = size;
				}
			}
			for(int i=maxNum; i>0; i--) {
				for(Entry<Post, Integer> entry : posts.entrySet()) {
					if(entry.getValue() == i) {
						sortedPosts.add(entry.getKey());
					}
				}
			}
			for(int i=0; i<3; i++) {
				favouritePosts.add(sortedPosts.get(i));
			}
			
			
			model.addAttribute("authors", as.getAllAuthors());
			model.addAttribute("tags", ts.getAllTags());
			model.addAttribute("favouritePosts", favouritePosts);
			model.addAttribute("posts", searchResults);	
			return "index";
					
		}
	}
	
	@RequestMapping("/posts_by_tag/{tag}")
	public String showPostsByTag(@PathVariable String tag, RedirectAttributes ra, Model model) {
		
		List<Post> searchResults = ps.getAllPostByTag(tag);
		if(searchResults.isEmpty()) {
			ra.addAttribute("error", "NO_POST_BY_TAG");
			return "redirect:/home";
		} else {
			List<Post> allPosts = ps.getAllPostsDesc(); 
			List<Post> sortedPosts = new ArrayList<>();
			List<Post> favouritePosts = new ArrayList<>();
			int maxNum = 0;
			
			Map<Post, Integer> posts = new HashMap<>();
			for(Post post : allPosts) {
				int size = post.getComments().size();
				posts.put(post, size);
				if(size > maxNum) {
					maxNum = size;
				}
			}
			for(int i=maxNum; i>0; i--) {
				for(Entry<Post, Integer> entry : posts.entrySet()) {
					if(entry.getValue() == i) {
						sortedPosts.add(entry.getKey());
					}
				}
			}
			for(int i=0; i<3; i++) {
				favouritePosts.add(sortedPosts.get(i));
			}
			
			model.addAttribute("authors", as.getAllAuthors());
			model.addAttribute("tags", ts.getAllTags());
			model.addAttribute("favouritePosts", favouritePosts);
			model.addAttribute("posts", searchResults);	
			return "index";
					
		}
	}
	
	@RequestMapping("/posts_by_author/{authorId}")
	public String showPostsByAuthor(@PathVariable String authorId, RedirectAttributes ra, Model model) {
		
		Author author = as.getAuthorById(authorId);
		List<Post> searchResults = ps.getAllPostByAuthor(author);
		if(searchResults.isEmpty()) {
			ra.addAttribute("error", "NO_POST_BY_AUTHOR");
			return "redirect:/home";
		} else {
			List<Post> allPosts = ps.getAllPostsDesc(); 
			List<Post> sortedPosts = new ArrayList<>();
			List<Post> favouritePosts = new ArrayList<>();
			int maxNum = 0;
			
			Map<Post, Integer> posts = new HashMap<>();
			for(Post post : allPosts) {
				int size = post.getComments().size();
				posts.put(post, size);
				if(size > maxNum) {
					maxNum = size;
				}
			}
			for(int i=maxNum; i>0; i--) {
				for(Entry<Post, Integer> entry : posts.entrySet()) {
					if(entry.getValue() == i) {
						sortedPosts.add(entry.getKey());
					}
				}
			}
			for(int i=0; i<3; i++) {
				favouritePosts.add(sortedPosts.get(i));
			}
			
			model.addAttribute("authors", as.getAllAuthors());
			model.addAttribute("tags", ts.getAllTags());
			model.addAttribute("favouritePosts", favouritePosts);
			model.addAttribute("posts", searchResults);	
			return "index";
					
		}
	}

	
	
}
