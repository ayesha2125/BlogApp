package com.blog.BlogApp.Repository;

import com.blog.BlogApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Optional: method to find user by email for login
    User findByEmail(String email);
}
