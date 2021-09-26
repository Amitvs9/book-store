//package com.store.book.util;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
///**
// * @author Amit Vs
// */
//@Component
//public class AuthUtil {
//
//    private final String secret = "javatechie";
//
//    public String getUserName(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public String generateToken(String userName){
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userName);
//
//    }
//
//    private String createToken(Map<String, Object> claims , String userName){
//        return Jwts.builder()
//                        .setSubject(userName)
//                        .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
//                        .signWith(SignatureAlgorithm.HS256, secret).compact();
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails){
//
//        final String userName= getUserName(token);
//        return (userName.equals(userDetails.isEnabled()) && !isTokenExpired(token));
//    }
//
//    private boolean isTokenExpired(String token){
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token){
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private <T> T extractClaim(String token , Function<Claims, T> claimsTFunction){
//        final Claims claims = extractAllClaim(token);
//       return claimsTFunction.apply(claims);
//
//    }
//
//    private Claims extractAllClaim(String token){
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//    }
//}
