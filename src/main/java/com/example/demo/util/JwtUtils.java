package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.UUID;

public class JwtUtils {

   private long time = 1000*60*60*24;
   private String signature = "user";
   public  String creatjwt(String phone,String password){
      JwtBuilder jwtBuilder = Jwts.builder();
      String JwtToken = jwtBuilder
              .setHeaderParam("typ","JWT")
              .setHeaderParam("alg","HS256")
              .claim("phone",phone)
              .claim("password",password)
              .setSubject("admin-test")
              .setExpiration(new Date(System.currentTimeMillis()+time))
              .setId(UUID.randomUUID().toString())
              .signWith(SignatureAlgorithm.HS512,signature)
              .compact();
      return JwtToken;
   }
   public Claims parseJwt(String token) {
      Claims claims = Jwts.parser().setSigningKey(signature).parseClaimsJws(token).getBody();
      return claims;
   }

}
