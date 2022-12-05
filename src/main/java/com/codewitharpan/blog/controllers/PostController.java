package com.codewitharpan.blog.controllers;

import com.codewitharpan.blog.config.AppConstants;
import com.codewitharpan.blog.entities.Post;
import com.codewitharpan.blog.payloads.ApiResponse;
import com.codewitharpan.blog.payloads.PostDto;
import com.codewitharpan.blog.payloads.PostResponse;
import com.codewitharpan.blog.services.FileService;
import com.codewitharpan.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {


    @Autowired
    private   PostService postService;



    @Autowired
    private FileService fileService;


    @Value("${project.image}")
    private  String path;



    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto>  createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable Integer categoryId){

        PostDto createPost=this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);

    }

    //get by user

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Integer userId) {

        List<PostDto>  postDtos=  this.postService.getPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);

    }

    // get by category


    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>>  getPostsByCategory(@PathVariable("categoryId") Integer categoryId) {

        List<PostDto> postDtos=this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }


    @GetMapping("/posts")
    public  ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue= AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value="sortBy",defaultValue =AppConstants.SORT_BY,required = false ) String sortBy,
            @RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required = false) String sortDir   ) {

        PostResponse postDtos=this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return  new ResponseEntity<PostResponse>(postDtos,HttpStatus.OK);
    }


    @GetMapping("/post/{postId}")
    public  ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto post=this.postService.getPostById(postId);
        return  new ResponseEntity<>(post,HttpStatus.OK);

    }

    @DeleteMapping("/post/{postId}")

    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return  new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted successfully",true),HttpStatus.OK);
    }


    @PutMapping("/post/{postId}")

    public ResponseEntity<PostDto> updatePost(@PathVariable Integer postId,@RequestBody PostDto postDto) {
      PostDto updatedPostDto=   this.postService.updatePost(postDto,postId);
      return new ResponseEntity<>(updatedPostDto,HttpStatus.OK);
    }






    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPost(@PathVariable("keywords") String keyword) {

        List<PostDto> postDtos=  this.postService.searchPost(keyword);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }


    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam ("image") MultipartFile image,
    @PathVariable("postId") Integer postId) throws IOException {
        PostDto postDto=  this.postService.getPostById(postId);
        String fileName=  this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatedPost=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);

    }




    @GetMapping(value = "post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable ("imageName")String imageName, HttpServletResponse response) throws IOException {
        InputStream  resource=this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }


}
