package com.codewitharpan.blog.repositories;

import com.codewitharpan.blog.entities.Category;
import com.codewitharpan.blog.entities.Post;
import com.codewitharpan.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    // custom finder method
    List<Post> findByUser(User user);

    // custom finder method
    List<Post> findByCategory(Category category);


    @Query("select p from Post p where p.title  like  :key")
    List<Post> findByTitleContaining(@Param("key") String title);

}
