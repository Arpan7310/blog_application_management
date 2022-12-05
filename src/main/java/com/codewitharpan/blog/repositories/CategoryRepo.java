package com.codewitharpan.blog.repositories;

import com.codewitharpan.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepo extends JpaRepository<Category,Integer> {



}
