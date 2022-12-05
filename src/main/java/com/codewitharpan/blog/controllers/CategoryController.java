package com.codewitharpan.blog.controllers;

import com.codewitharpan.blog.payloads.ApiResponse;
import com.codewitharpan.blog.payloads.CategoryDto;
import com.codewitharpan.blog.repositories.CategoryRepo;
import com.codewitharpan.blog.services.CategoryService;
import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;



    @PostMapping("/")
    public ResponseEntity<CategoryDto> createcCategory(@Valid  @RequestBody CategoryDto categoryDto) {
       CategoryDto categoryDto1= this.categoryService.createCategory(categoryDto);
       return  new ResponseEntity<CategoryDto>(categoryDto1, HttpStatus.CREATED);
    }


    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId) {
      CategoryDto updatedCategory=  this.categoryService.updateCategory(categoryDto,categoryId);
      return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
    }


    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);
        return  new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successfully",true),HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
      CategoryDto categoryDto= this.categoryService.getCategory(categoryId);
      return  new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
      List<CategoryDto> categoryDtos= this.categoryService.getCategories();
      return  new ResponseEntity<List<CategoryDto>>(categoryDtos,HttpStatus.OK);
    }

}
