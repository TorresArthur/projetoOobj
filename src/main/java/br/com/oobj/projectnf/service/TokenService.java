package br.com.oobj.projectnf.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${easybill.jwt.secret}")
    private String secret;


    public String geraToken() {
        Date hoje = new Date();
        return Jwts.builder()
                .setIssuer("ProjectNF")
                .setSubject("API-Key")
                .setIssuedAt(hoje)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}

