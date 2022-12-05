package com.codewitharpan.blog.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    public static  final long JWT_TOKEN_VALIDITY=5*60*60;


    private  String secret="jwtTokenKey";



    //retrieve username from token
    public  String getUserNameFromToken(String token) {
        return getClaimFromToken(token,(Claims)-> Claims.getSubject());
    }

    //retrieve expiration from token
    public Date getExpirationDateFromToken(String token) {
        return  getClaimFromToken(token,Claims::getExpiration);
    }


    public <T>  T getClaimFromToken(String token, Function<Claims,T> claimsResolver) {
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    // for retrieving any information from token we will need secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if token is expired
    private  boolean isTokenExpired(String token) {
        final  Date expiration=getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generate token from user
    public  String generateToken(UserDetails userDetails) {
        Map<String,Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());


    }


    //while generating token
    // 1.define the claims of token like issuer ,expiration,subject and id
    //2.sign the jwt using HS512 algorithm and secret key
    //3.According to jws Compact serialization compaction of the JWT to a url-safe string
    private  String doGenerateToken(Map<String,Object> claims,String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +JWT_TOKEN_VALIDITY*10000)).signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    //validate token
    public   boolean validateToken(String token ,UserDetails userDetails) {
        final String username=getUserNameFromToken(token);
        return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
