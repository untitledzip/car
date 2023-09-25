package com.example.car.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.Kernel;
import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
    /*
        EXPIRATIONTIME: 토큰의 만료 시간을 밀리초 단위로 정의
        PREFIX: 토큰의 접두사, 일반적으로 Bearer 스키마를 이용
        secretKeyFor(): jjwt 라이브러리, 비밀 키 생성
        getToken(): 토큰을 생성하고 반환
        getAuthUser(): 응답의 Authorization 헤더에서 토큰을 가져옴
        parserBuilder(): jjwt 라이브러리, JwtParserBuilder 인스턴스 생성
        setSigningKey(): 토큰 검증을 위한 비밀 키 지정
        getSubject(): 사용자 이름 얻음
    */

    static final long EXPIRATIONTIME = 86400000; //1일을 밀리초로 계산한 값
    static final String PREFIX = "Bearer";

    //비밀 키 생성, 시연 용도로만 이용해야 함
    //애플리케이션 구성에서 읽을 수 있음
    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //서명된 JWT 토큰 생성
    public String getToken(String username){
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(key)
                .compact();

        return token;
    }

    //요청 권한 부여 헤더에서 토큰을 가져와 토큰을 확인하고 사용자 이름을 얻음
    public String getAuthUser(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(token != null){
            String user = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(PREFIX,""))
                    .getBody()
                    .getSubject();

            if(user != null)
                return user;
        }

        return null;
    }
}
