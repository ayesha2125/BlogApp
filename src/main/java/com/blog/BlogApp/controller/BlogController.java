package com.blog.BlogApp.controller;

import com.blog.BlogApp.model.Blog;
import com.blog.BlogApp.model.User;
import com.blog.BlogApp.Repository.BlogRepository;
import com.blog.BlogApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@CrossOrigin(origins = "*")
public class BlogController {

    @Autowired
    private BlogRepository blogRepo;

    @Autowired
    private UserRepository userRepo;

    // ✅ CREATE BLOG (by userId)
    @PostMapping("/{userId}")
    public Blog createBlog(@PathVariable Long userId, @RequestBody Blog blog) {
        User author = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        blog.setAuthor(author);
        return blogRepo.save(blog);
    }

    // ✅ GET ALL BLOGS
    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogRepo.findAll();
    }

    // ✅ GET BLOG BY BLOG ID
    @GetMapping("/{blogId}")
    public Blog getBlogById(@PathVariable Long blogId) {
        return blogRepo.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found with ID: " + blogId));
    }

    // ✅ GET ONLY PUBLISHED BLOGS
    @GetMapping("/published")
    public List<Blog> getPublishedBlogs() {
        return blogRepo.findByStatus("PUBLISHED");
    }


}
