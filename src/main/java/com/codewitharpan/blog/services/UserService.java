package com.codewitharpan.blog.services;

import com.codewitharpan.blog.payloads.RoleDto;
import com.codewitharpan.blog.payloads.UserDto;

import java.util.List;
import java.util.Set;

public interface UserService {




   UserDto registerNewUser(UserDto userDto);

   UserDto createUser(UserDto user, Set<RoleDto> roleDto) throws Exception;

   UserDto updateUser(UserDto user,Integer userId);

   UserDto getUserById(Integer userId);

   List<UserDto> getAllUsers();

   void deleteUser(Integer userId);


   List<RoleDto> getRolesByUser(Integer userId);


   List<UserDto> getUsersByRole(Integer roleId);


}
