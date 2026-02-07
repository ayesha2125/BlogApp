package com.blog.BlogApp.controller;

import com.blog.BlogApp.model.Blog;
import com.blog.BlogApp.model.Comment;
import com.blog.BlogApp.model.User;
import com.blog.BlogApp.Repository.BlogRepository;
import com.blog.BlogApp.Repository.CommentRepository;
import com.blog.BlogApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private BlogRepository blogRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/{blogId}/{userId}")
    public Comment addComment(@PathVariable Long blogId, @PathVariable Long userId, @RequestBody Comment comment) {
        Blog blog = blogRepo.findById(blogId).orElseThrow(() -> new RuntimeException("Blog not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        comment.setBlog(blog);
        comment.setUser(user);
        return commentRepo.save(comment);
    }

    @GetMapping("/{blogId}")
    public List<Comment> getComments(@PathVariable Long blogId) {
        return commentRepo.findByBlog_BlogId(blogId);
    }
}
