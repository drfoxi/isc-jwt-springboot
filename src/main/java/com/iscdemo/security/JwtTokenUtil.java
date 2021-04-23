package com.iscdemo.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import com.iscdemo.models.basemodel.MainSecurityContext;
import com.iscdemo.models.constant.ProjectConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public MainSecurityContext getMainSecurityContextFromToken(String token) {
        if (token != null && token.startsWith("Bearer "))
            token = token.substring(7);
        MainSecurityContext msc = new MainSecurityContext();
        LinkedHashMap<String, Object> claims = (LinkedHashMap<String, Object>) getAllClaimsFromToken(token).get("payload");
        msc.setFirstName((String) claims.get("firstName"));
        msc.setLastName((String) claims.get("lastName"));
        msc.setUsername((String) claims.get("username"));
        msc.setChannel((Integer) claims.get("channel"));
        msc.setUserRole((Integer) claims.get("userRole"));
        msc.setId((Integer) claims.get("id"));
        msc.setIp((String) claims.get("ip"));
        msc.setMac((String) claims.get("mac"));
        msc.setRegisterDate((Long) claims.get("registerDate"));
        msc.setLoginDate((Long) claims.get("loginDate"));
        msc.setToken(token);
        return msc;
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(MainSecurityContext msc) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("payload", msc);
        return doGenerateToken(claims, msc.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ProjectConstant.JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}