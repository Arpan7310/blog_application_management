package com.codewitharpan.blog.services.impl;

import com.codewitharpan.blog.config.AppConstants;
import com.codewitharpan.blog.entities.Role;
import com.codewitharpan.blog.entities.User;
import com.codewitharpan.blog.exceptions.ResourceNotFoundException;
import com.codewitharpan.blog.payloads.RoleDto;
import com.codewitharpan.blog.payloads.UserDto;
import com.codewitharpan.blog.repositories.RoleRepo;
import com.codewitharpan.blog.repositories.UserRepo;
import com.codewitharpan.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {




    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private PasswordEncoder passwordEncoder;




    @Override
    public UserDto createUser(UserDto userDto, Set<RoleDto> roleDto) throws Exception {

      User userfound=this.userRepo.getByEmail(userDto.getEmail());

        if( userfound !=null) {
            throw  new ResourceNotFoundException("Email","Email already present "+userDto.getEmail(),0);
        }
        else {
            userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            User user = this.dtoToUser(userDto);
            Set<Role> roles = roleDto.stream().map((role) -> this.modelMapper.map(role, Role.class)).collect(Collectors.toSet());
            user.setRoles(roles);
            User savedUser = this.userRepo.save(user);
            return this.userToDto(savedUser);
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=this.userRepo.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser=this.userRepo.save(user);
        UserDto userDto1=  this.userToDto(updatedUser);
        return userDto1;

    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users=this.userRepo.findAll();
        //vv-imp
        List<UserDto> userDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        this.userRepo.delete(user);

    }


    private User dtoToUser(UserDto userDto) {

      //  User user=new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
       User user=  this.modelMapper.map(userDto,User.class);
        return  user;
    }


    public UserDto userToDto(User user) {
      //   UserDto userDto=new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        UserDto userDto=this.modelMapper.map(user, UserDto.class);
          return userDto;
    }


    @Override
    public  List<RoleDto> getRolesByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user id",userId));
        List<Role> roles=this.roleRepo.findByUsers(user);
        List<RoleDto> roleDtos=roles.stream().map((role)->this.modelMapper.map(role,RoleDto.class)).collect(Collectors.toList());
        return  roleDtos;
    }

    @Override
    public List<UserDto> getUsersByRole(Integer roleId) {
        Role role=this.roleRepo.findById(roleId).orElseThrow(()->new ResourceNotFoundException("Role","role id",roleId));
        List<User> users=this.userRepo.findByRoles(role);
        List<UserDto> userDtos=users.stream().map((user)->this.modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }


    @Override
    public UserDto registerNewUser(UserDto userDto) {
       User user= this.modelMapper.map(userDto,User.class);
       user.setPassword(this.passwordEncoder.encode(user.getPassword()));


       //roles
       Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();

       user.getRoles().add(role);
       User savedUser=this.userRepo.save(user);

       return this.modelMapper.map(savedUser, UserDto.class);
    }



}
