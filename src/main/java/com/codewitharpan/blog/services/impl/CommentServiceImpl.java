package com.codewitharpan.blog.services.impl;

import com.codewitharpan.blog.entities.Comment;
import com.codewitharpan.blog.entities.Post;
import com.codewitharpan.blog.exceptions.ResourceNotFoundException;
import com.codewitharpan.blog.payloads.CommentDto;
import com.codewitharpan.blog.payloads.PostDto;
import com.codewitharpan.blog.repositories.CommentRepo;
import com.codewitharpan.blog.repositories.PostRepo;
import com.codewitharpan.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {



    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",postId));
        Comment comment=this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setContent(commentDto.getContent());
        Comment savedComment=this.commentRepo.save(comment);
        CommentDto commentDto1=this.modelMapper.map(savedComment,CommentDto.class);
        return commentDto1;
    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment id",commentId));
        this.commentRepo.delete(comment);
    }
}
