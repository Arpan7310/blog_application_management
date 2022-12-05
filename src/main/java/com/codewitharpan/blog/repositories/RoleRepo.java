package com.codewitharpan.blog.repositories;

import com.codewitharpan.blog.entities.Role;
import com.codewitharpan.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role,Integer> {


    List<Role> findByUsers(User user);
}
