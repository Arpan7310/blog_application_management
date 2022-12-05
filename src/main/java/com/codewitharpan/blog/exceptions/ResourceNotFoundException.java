package com.codewitharpan.blog.exceptions;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Access;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private long fieldValue;

    public ResourceNotFoundException (String resourceName,String fieldName,long fieldValue) {
        super(String.format("%s not found with this %s: :%s",resourceName,fieldName,fieldValue));
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }

}
