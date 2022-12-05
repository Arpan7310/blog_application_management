package com.codewitharpan.blog.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {


    private Integer categoryId;

    @NotBlank
    @Size(min=4,message = "Minimum size of category title is 4")
    private String categoryTitle;

    @NotBlank
    @Size(min=10, message = "Minimum size of category description is 10")
    private String categoryDescription;


}
