package com.codewitharpan.blog.services.impl;

import com.codewitharpan.blog.entities.Category;
import com.codewitharpan.blog.exceptions.ResourceNotFoundException;
import com.codewitharpan.blog.payloads.CategoryDto;
import com.codewitharpan.blog.repositories.CategoryRepo;
import com.codewitharpan.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepo categoryRepo;


    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category=this.modelMapper.map(categoryDto,Category.class);
        Category savedCategory=this.categoryRepo.save(category);
        return  this.modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        Category updatedcat=this.categoryRepo.save(category);
        return this.modelMapper.map(updatedcat,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {

       Category cat =this.categoryRepo.findById(categoryId).
               orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));

       this.categoryRepo.delete(cat);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {

        Category foundCategory=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        return this.modelMapper.map(foundCategory,CategoryDto.class);

    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories=this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos=categories.stream().map((e)->this.modelMapper.map(e,CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }
}
