package com.codewitharpan.blog.services;

import com.codewitharpan.blog.payloads.CommentDto;

public interface CommentService {


    CommentDto createComment(CommentDto commentDto,Integer postId);


    void deleteComment(Integer commentId);
}
