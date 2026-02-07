package com.blog.BlogApp.controller;

import com.blog.BlogApp.model.Blog;
import com.blog.BlogApp.model.Like;
import com.blog.BlogApp.model.User;
import com.blog.BlogApp.Repository.BlogRepository;
import com.blog.BlogApp.Repository.LikeRepository;
import com.blog.BlogApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeRepository likeRepo;

    @Autowired
    private BlogRepository blogRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/{blogId}/{userId}")
    public String likeBlog(@PathVariable Long blogId, @PathVariable Long userId) {
        if (likeRepo.existsByBlog_BlogIdAndUser_UserId(blogId, userId)) {
            return "Already liked";
        }
        Blog blog = blogRepo.findById(blogId).orElseThrow(() -> new RuntimeException("Blog not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Like like = new Like();
        like.setBlog(blog);
        like.setUser(user);
        likeRepo.save(like);
        return "Blog liked!";
    }
}
