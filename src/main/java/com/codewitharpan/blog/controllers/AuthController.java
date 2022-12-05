package com.codewitharpan.blog.controllers;

import com.codewitharpan.blog.exceptions.ApiException;
import com.codewitharpan.blog.payloads.JwtAuthRequest;
import com.codewitharpan.blog.payloads.JwtAuthResponse;
import com.codewitharpan.blog.payloads.UserDto;
import com.codewitharpan.blog.security.JwtTokenHelper;
import com.codewitharpan.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {


    @Autowired
    private JwtTokenHelper jwtTokenHelper;


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;



    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws  Exception{
        this.authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails= this.userDetailsService.loadUserByUsername(request.getUsername());
        String generateToken=this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
        jwtAuthResponse.setToken(generateToken);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws  Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        try {
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (BadCredentialsException e) {
            throw new ApiException("Invalid username or password");
        }

    }


    //register new user

    @PostMapping("/register")
    public ResponseEntity<UserDto>  registerUser(@RequestBody UserDto userDto){
       UserDto registeredUser=  this.userService.registerNewUser(userDto);
       return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }
}
