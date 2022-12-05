package com.codewitharpan.blog.repositories;

import com.codewitharpan.blog.entities.Role;
import com.codewitharpan.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {


    Optional<User> findByEmail(String email);

    @Query("Select p from User p where p.email =:email")
    User getByEmail(@Param("email") String email);


    List<User> findByRoles(Role role);

}
