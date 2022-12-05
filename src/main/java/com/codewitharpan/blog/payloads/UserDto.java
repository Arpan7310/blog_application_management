package com.codewitharpan.blog.payloads;


import com.codewitharpan.blog.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min=4,message = "Username must be minimum of 4 characters")
    private String name;
    @Email(message = "Your given email address is not valid")
    private String email;
    @NotEmpty
    @Size(min=3,max = 10,message = "password must be minimum of 3 chars and max of 10 chars")
    private String password;
    @NotEmpty
    private String about;


    private Set<RoleDto> roles= new HashSet<>();

}
