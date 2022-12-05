package com.codewitharpan.blog.services;

import com.codewitharpan.blog.entities.Category;
import com.codewitharpan.blog.entities.Post;
import com.codewitharpan.blog.payloads.PostDto;
import com.codewitharpan.blog.payloads.PostResponse;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface PostService {


    //create
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto,Integer postId);

    //delete
    void deletePost(Integer postId);


    //get all posts
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);


    //get single post
    PostDto getPostById(Integer postId);

    //get all post By Category
    List<PostDto> getPostByCategory(Integer categoryId);

    // get all post by User
    List<PostDto> getPostByUser(Integer userId);

    //search post by keyword
    List<PostDto> searchPost(String keyword);





}
