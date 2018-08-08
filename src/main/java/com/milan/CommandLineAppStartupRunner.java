package com.milan;

import com.milan.entity.Author;
import com.milan.entity.Comment;
import com.milan.entity.Post;
import com.milan.entity.Tag;
import com.milan.service.AuthorService;
import com.milan.service.CommentService;
import com.milan.service.PostService;
import com.milan.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private AuthorService as;

    @Autowired
    private TagService ts;

    @Autowired
    private PostService ps;

    @Autowired
    private CommentService cs;

    @Override
    public void run(String...args) throws Exception {

        // Adding first Author
        Author author = new Author();
        author.setFirstName("Milan");
        author.setLastName("Beronja");
        author.setPassword("11111982");
        author.setUserName("milan");
        author.setAuthorImg("ZaLaptopom.jpg");
        as.createNewAuthor(author);


        // Adding firts Tag
        Tag tag = new Tag();
        tag.setTagName("tourism");
        ts.putNewTag(tag);

        // Adding first Post
        List<Author> authors = as.getAllAuthors();
        List<Tag> tags = ts.getAllTags();

        int authorId = 0;
        for(Author author1 : authors){
            if("11111982".equals(author.getPassword())){
                authorId = author1.getAuthorId();
            }
        }
        Author author2 = as.getAuthorById(Integer.toString(authorId));

        int tagId = 0;
        for(Tag tag1 : tags){
            if("tourism".equals(tag1.getTagName())){
                tagId = tag1.getTagId();
            }
        }

        Post post = new Post();
        post.setAuthor(author2);
        post.setImage("belgrade.jre");
        post.setBody("This is whole text about Belgrade");
        post.setGist("This is short text about Belgrade");
        post.setTitle("BELGRADE - WORLD WIDE KNOWN DESTINATION!");
        post.setSubtitle("Belgrade is one of most popular Europian destination");
        post.setTag(Integer.toString(tagId));

        ps.saveNewPost(post);

        // Adding second post
        Post post2 = new Post();
        post2.setAuthor(author2);
        post2.setImage("belgrade.jre");
        post2.setBody("This is whole text about Belgrade2");
        post2.setGist("This is short text about Belgrade2");
        post2.setTitle("BELGRADE - WORLD WIDE KNOWN DESTINATION2!");
        post2.setSubtitle("Belgrade is one of most popular Europian destination2");
        post2.setTag(Integer.toString(tagId));

        ps.saveNewPost(post2);

        // Adding third post
        Post post3 = new Post();
        post3.setAuthor(author2);
        post3.setImage("belgrade.jre");
        post3.setBody("This is whole text about Belgrade3");
        post3.setGist("This is short text about Belgrade3");
        post3.setTitle("BELGRADE - WORLD WIDE KNOWN DESTINATION3!");
        post3.setSubtitle("Belgrade is one of most popular Europian destination3");
        post3.setTag(Integer.toString(tagId));

        ps.saveNewPost(post3);

        // Add first Comment
        Comment comment = new Comment();
        comment.setUserImg("marko.jre");
        comment.setComment("Ovaj post je stvarno strava");
        comment.setLiked(true);
        comment.setUserName("marko");
        Post post4 = ps.getPostByTitle("BELGRADE - WORLD WIDE KNOWN DESTINATION!");
        comment.setPost(post4);

        cs.saveComment(comment);

        // Add second Comment
        Comment comment2 = new Comment();
        comment2.setUserImg("Petra.jpg");
        comment2.setComment("Ovaj post je stvarno strava");
        comment2.setLiked(false);
        comment2.setUserName("petra");
        Post post5 = ps.getPostByTitle("BELGRADE - WORLD WIDE KNOWN DESTINATION2!");
        comment2.setPost(post5);

        cs.saveComment(comment2);

        // Add third Comment
        Comment comment3 = new Comment();
        comment3.setUserImg("marko.jre");
        comment3.setComment("Ovaj post je stvarno strava3");
        comment3.setLiked(true);
        comment3.setUserName("marko");
        Post post6 = ps.getPostByTitle("BELGRADE - WORLD WIDE KNOWN DESTINATION3!");
        comment3.setPost(post6);

        cs.saveComment(comment3);

    }
}
