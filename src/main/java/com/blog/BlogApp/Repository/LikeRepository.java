package com.blog.BlogApp.Repository;

import com.blog.BlogApp.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByBlog_BlogIdAndUser_UserId(Long blogId, Long userId);
}
