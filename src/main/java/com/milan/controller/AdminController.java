package com.milan.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.milan.entity.Author;
import com.milan.entity.Post;
import com.milan.entity.Tag;
import com.milan.service.AuthorService;
import com.milan.service.PostService;
import com.milan.service.TagService;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private static String UPLOADED_FOLDER = "\\src\\main\\resources\\static\\Slike\\";
	
	@Autowired
	AuthorService as;
	
	@Autowired
	TagService ts;
	
	@Autowired
	PostService ps;
	
	@GetMapping({"","/"})
	public String menageBlogError(RedirectAttributes ra) {
		ra.addAttribute("error", "INVALID_ADMIN");
		return "redirect:/home";
	} 
	
	@GetMapping("/logout")
	public String logOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("author", null);
		return "redirect:/";
	}
	
	@PostMapping({"","/"})
	public String menageBlog(@ModelAttribute Author logInAuthor, HttpServletRequest request, RedirectAttributes ra) {
		
		Author author = as.chechAuthor(logInAuthor.getUserName(), logInAuthor.getPassword());
		if (author != null) {
			HttpSession session = request.getSession();
			session.setAttribute("author", author);
			return "redirect:admin/manageBlog";
		} else {
			ra.addAttribute("error", "INVALID_AUTHOR");
			return "redirect:/home";
		} 
	}
	
	@RequestMapping("/manageBlog/error")
	public String showManageBlogPageWithError(Model model, @RequestParam("error") String error, 
											   RedirectAttributes ra, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("author") != null) {
			switch (error) {
			case "INVALID_PASSWORD" : model.addAttribute("mode", "ADD_NEW_AUTHOR" );
				break;
			default: model.addAttribute("mode", "MANAGE_BLOG" );
				break;
			}
			model.addAttribute("authors", as.getAllAuthors());
			model.addAttribute("tags", ts.getAllTags());
			model.addAttribute("error", error);
			model.addAttribute("loggedAuthor", session.getAttribute("author")); 
			return "views/managePost";
		}else {
			ra.addAttribute("error", "INVALID_ADMIN");
			return "redirect:/home";
		}
	}
	
	@RequestMapping("/manageBlog")
	public String showManageBlogPageWithoutError(Model model, RedirectAttributes ra, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("author") != null) {
			List<Post> posts = ps.getAllPosts();
			List<String> titles = new ArrayList<>();
			for(Post post : posts) {
				titles.add(post.getTitle());
				}
			model.addAttribute("mode", "MANAGE_BLOG");
			model.addAttribute("loggedAuthor", session.getAttribute("author")); 
			model.addAttribute("authors", as.getAllAuthors());
			model.addAttribute("tags", ts.getAllTags());
			model.addAttribute("titles", titles);
			return "views/managePost";
		}else {
			ra.addAttribute("error", "INVALID_ADMIN");
			return "redirect:/home";
		}
	}
	
	@RequestMapping("/newAuthor")
	public String NewAuthor(Model model, RedirectAttributes ra, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("author") == null) {
			ra.addAttribute("error", "INVALID_AUTHOR");
			return "redirect:/home";
		}else {
			model.addAttribute("mode", "ADD_NEW_AUTHOR");
			model.addAttribute("loggedAuthor", session.getAttribute("author")); 
			return "views/managePost";
		}
	}
	
	@PostMapping(value="/newAuthor/addNew")
	public String addNewAuthor(@ModelAttribute Author author, @RequestParam("confirmedPassword") String confirmedPassword, 
								@RequestParam("file") MultipartFile file, HttpServletRequest request, RedirectAttributes ra) {
		
		HttpSession session = request.getSession();
		if(session.getAttribute("author") != null) {
			if (confirmedPassword.equals(author.getPassword())) {
				author.setAuthorImg(file.getOriginalFilename());
				as.createNewAuthor(author);
				try {
					byte[] butes = file.getBytes();
					Path currentRelativePath = Paths.get("");
					String s = currentRelativePath.toAbsolutePath().toString();
					Path path = Paths.get(s + UPLOADED_FOLDER + file.getOriginalFilename());
					Files.write(path, butes);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return  "redirect:/admin/manageBlog";
				
			} else {
				ra.addAttribute("error", "INVALID_PASSWORD");
				return  "redirect:/admin/manageBlog/error";
			}
			
		} else {
			ra.addAttribute("error", "INVALID_ADMIN");
			return "redirect:/home";
		}
	}
	
	@RequestMapping("/newPost")
	public String showNewPostForm(Model model, HttpServletRequest request, RedirectAttributes ra) {
		
		HttpSession session = request.getSession();
		if(session.getAttribute("author") != null) {
			model.addAttribute("mode", "ADD_NEW_POST");
			model.addAttribute("authors", as.getAllAuthors());
			model.addAttribute("tags", ts.getAllTags());
			model.addAttribute("loggedAuthor", session.getAttribute("author")); 
			return "views/managePost";
		} else {
			ra.addAttribute("error", "INVALID_ADMIN");
			return "redirect:/home"; 
		}
	}
	
	@PostMapping("/newPost/addNew")
	public String addNewPost(@RequestParam("authorId") String authorId, @ModelAttribute Post post, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		Author author = as.getAuthorById(authorId);
		post.setAuthor(author);
		
		ps.saveNewPost(post);		
		
		model.addAttribute("mode", "MANAGE_BLOG");
		model.addAttribute("authors", as.getAllAuthors());
		model.addAttribute("tags", ts.getAllTags());
		model.addAttribute("loggedAuthor", session.getAttribute("author")); 
		return "views/managePost";
	}
	
	
	@RequestMapping("/newTag")
	public String showNewTagForm(Model model, HttpServletRequest request, RedirectAttributes ra) {
		HttpSession session = request.getSession();
		if(session.getAttribute("author") != null) {
			model.addAttribute("mode", "ADD_NEW_TAG");
			model.addAttribute("loggedAuthor", session.getAttribute("author")); 
			return "views/managePost";
		} else {
			ra.addAttribute("error", "INVALID_ADMIN");
			return "redirect:/home";
		}
		
	}
	
	@RequestMapping("/newTag/addNew")
	public String addNewTag(@RequestParam("tagName") String tagName, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		Tag tag = new Tag();
		tag.setTagName(tagName);
		ts.putNewTag(tag);
		model.addAttribute("mode", "MANAGE_BLOG");
		model.addAttribute("authors", as.getAllAuthors());
		model.addAttribute("tags", ts.getAllTags());
		model.addAttribute("loggedAuthor", session.getAttribute("author")); 
		return "views/managePost";
	}
	
	@RequestMapping("/tag/{tagId}")
	public String filterPostByTag(@PathVariable String tagId, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		
		ArrayList<String> titles = new ArrayList<String>();
		List<Post> posts = ps.getAllPostByTag(tagId);
		for(Post post : posts) {
			titles.add(post.getTitle());
		}
		
	
		model.addAttribute("titlesByTag", titles);		
		model.addAttribute("mode", "MANAGE_BLOG");
		model.addAttribute("authors", as.getAllAuthors());
		model.addAttribute("tags", ts.getAllTags());
		model.addAttribute("loggedAuthor", session.getAttribute("author")); 
		return "views/managePost";
		
	}
	
	@RequestMapping("/author/{authorId}")
	public String filterPostByAuthor(@PathVariable String authorId, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		
		ArrayList<String> titles = new ArrayList<String>();
		Author author = as.getAuthorById(authorId);
		List<Post> posts = ps.getAllPostByAuthor(author);  
		
		for(Post post : posts) {
			titles.add(post.getTitle());
		}
		
	
		model.addAttribute("titlesByAuthor", titles);		
		model.addAttribute("mode", "MANAGE_BLOG");
		model.addAttribute("authors", as.getAllAuthors());
		model.addAttribute("tags", ts.getAllTags());
		model.addAttribute("loggedAuthor", session.getAttribute("author")); 
		return "views/managePost";
		
	}
	
	@RequestMapping("/newAuthors")
	public String NewAuthors(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		
		model.addAttribute("mode", "ADD_NEW_AUTHOR");
		model.addAttribute("loggedAuthor", session.getAttribute("author")); 
		return "views/managePost";
	
	}
	
	@PostMapping(value="/newAuthors/addNew")
	public String addNewAuthors(@ModelAttribute Author author, @RequestParam("file") MultipartFile file) {
		
		author.setAuthorImg(file.getOriginalFilename());
		as.createNewAuthor(author);
		
		return  "redirect:/admin/manageBlog";
			
	}


	

}
