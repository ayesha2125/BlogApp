package com.blog.BlogApp.Repository;

import com.blog.BlogApp.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByStatus(String status); // Optional: get only published blogs
}
