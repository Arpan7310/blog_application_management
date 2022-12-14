package com.codewitharpan.blog.controllers;

import com.codewitharpan.blog.payloads.ApiResponse;
import com.codewitharpan.blog.payloads.CommentDto;
import com.codewitharpan.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@RequestMapping("/api/")
public class CommentController {



    @Autowired
    private CommentService commentService;



    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId) {

        CommentDto commentDto1=  this.commentService.createComment(commentDto,postId);
        return  new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }


    @DeleteMapping("/comments/{commentId}")
    public  ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
         this.commentService.deleteComment(commentId);
         return  new ResponseEntity<>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
    }
}
