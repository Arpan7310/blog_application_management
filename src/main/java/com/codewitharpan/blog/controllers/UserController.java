package com.codewitharpan.blog.controllers;

import com.codewitharpan.blog.entities.Role;
import com.codewitharpan.blog.entities.User;
import com.codewitharpan.blog.exceptions.ResourceNotFoundException;
import com.codewitharpan.blog.payloads.ApiResponse;
import com.codewitharpan.blog.payloads.RoleDto;
import com.codewitharpan.blog.payloads.UserDto;
import com.codewitharpan.blog.repositories.RoleRepo;
import com.codewitharpan.blog.repositories.UserRepo;
import com.codewitharpan.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;




//POST

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) throws Exception {

        Set<RoleDto> roleDtos=  userDto.getRoles();
        UserDto createUserDto=this.userService.createUser(userDto,roleDtos);
        return  new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }


    @GetMapping("/roles/{userId}")
    public ResponseEntity<List<RoleDto>> getRoles(@PathVariable Integer userId){
        List<RoleDto>  roleDtos= this.userService.getRolesByUser(userId);
        return ResponseEntity.ok(roleDtos);
    }

//PUT


    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uId) {
        UserDto updatedUser= this.userService.updateUser(userDto,uId);
        return  ResponseEntity.ok(updatedUser);
    }


//DELETE


    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uId) {
         this.userService.deleteUser(uId);
         return new ResponseEntity<>(new ApiResponse("User deleted Successfully",true),HttpStatus.CREATED);
    }


//GET


    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }


    @GetMapping("/{userId}")
    public  ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId) {
        return  ResponseEntity.ok(this.userService.getUserById(userId));
    }


    @GetMapping("/rolesforuser/{roleId}")
    public  ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable Integer roleId) {
       List<UserDto> userDtos= this.userService.getUsersByRole(roleId);
       return new ResponseEntity<>(userDtos,HttpStatus.OK);
    }

}
