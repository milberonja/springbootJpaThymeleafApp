package com.milan.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.milan.entity.Author;
import com.milan.entity.Comment;
import com.milan.entity.Post;
import com.milan.entity.Tag;
import com.milan.service.AuthorService;
import com.milan.service.CommentService;
import com.milan.service.PostService;
import com.milan.service.TagService;

@Controller
@RequestMapping("/posts")
public class PostController {
	
	private static String UPLOADED_FOLDER = "\\src\\main\\resources\\static\\Slike\\";
	
	@Autowired
	AuthorService as;
	
	@Autowired
	TagService ts;
	
	@Autowired
	PostService ps;
	
	@Autowired
	CommentService cs;
	
	@RequestMapping("/{postId}")
	public String showPostById(@PathVariable String postId, Model model) {
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
	
		model.addAttribute("post", ps.getPostById(postId));
		model.addAttribute("authors", as.getAllAuthors());
		model.addAttribute("tags", ts.getAllTags());
		model.addAttribute("favouritePosts", favouritePosts);
		return "views/displayPost";
	}
	
	@RequestMapping("/update_post/{postTitle}")
	public String showUpdateForm(@PathVariable String postTitle, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		
		Post post = ps.getPostByTitle(postTitle);
		Tag tag = ts.getTagByTagId(post.getTag());
					
		model.addAttribute("mode", "UPDATE_POST");
		model.addAttribute("post", post);
		model.addAttribute("author", post.getAuthor());
		model.addAttribute("authors", as.getAllAuthors());
		model.addAttribute("tags", ts.getAllTags());
		model.addAttribute("selectedTag", tag);
		model.addAttribute("loggedAuthor", session.getAttribute("author")); 
			
		return "views/managePost";
	}
	
	@PostMapping("/update_post")
	public String updatePostById(@ModelAttribute Post post, 
								  @RequestParam("updatedImage") MultipartFile updatedImage, 
								  @RequestParam("authorId") String authorId,
								  HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		
		Author author = as.getAuthorById(authorId);
		post.setAuthor(author);
		
		if(updatedImage.isEmpty()) {
			ps.updatePost(post);
		} else {
			post.setImage(updatedImage.getOriginalFilename()); 
			try {
				byte[] butes = updatedImage.getBytes();
				Path currentRelativePath = Paths.get("");
				String s = currentRelativePath.toAbsolutePath().toString();
				Path path = Paths.get(s + UPLOADED_FOLDER + updatedImage.getOriginalFilename());
				Files.write(path, butes); 
			} catch (IOException e) {
				e.printStackTrace();
			}
			ps.updatePost(post); 
		}
		
		model.addAttribute("mode", "MANAGE_BLOG");
		model.addAttribute("authors", as.getAllAuthors());
		model.addAttribute("tags", ts.getAllTags());
		model.addAttribute("loggedAuthor", session.getAttribute("author")); 
			
		return "views/managePost";
		
	}
	
	@RequestMapping("/delete_post/{postTitle}")
	public String deletePost(@PathVariable String postTitle, Model model) {
		Post post = ps.getPostByTitle(postTitle);
		ps.deletePost(post);
		return "redirect:/admin/manageBlog";
	}
	
	@PostMapping("/add_comment")
	public String saveComment(@RequestParam("userName") String userName, 
							   @RequestParam("commentBody") String commentBody, 
							   @RequestParam("userImg") MultipartFile userImg,
							   @RequestParam("liked") boolean liked,
							   @RequestParam("postId") String postId) {
		Post post = ps.getPostById(postId);
		Comment comment = new Comment();
		if(userImg.getOriginalFilename().equals("")) {
			comment.setUserImg("person.jpg"); 
		}else {
			comment.setUserImg(userImg.getOriginalFilename());
			try {
				byte[] butes = userImg.getBytes();
				Path currentRelativePath = Paths.get("");
				String s = currentRelativePath.toAbsolutePath().toString();
				Path path = Paths.get(s + UPLOADED_FOLDER + userImg.getOriginalFilename());
				Files.write(path, butes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		comment.setUserName(userName);
		comment.setComment(commentBody);
		comment.setLiked(liked);
		comment.setPost(post);
		
		cs.saveComment(comment);
		return "redirect:/";
	}


}
