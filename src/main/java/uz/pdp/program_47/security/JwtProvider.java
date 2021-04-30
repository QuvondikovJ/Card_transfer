package uz.pdp.program_47.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {


    long expireTime = 60000000;
    String keyWord = "BuTokenUchunMaxfiySuzBuniHechkimBilmasin123";

    public String generateToken(String username){
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, keyWord)
                .compact();
        return  token;
    }

public boolean validateToken(String token){
        try{
            Jwts
                    .parser()
                    .setSigningKey(keyWord)
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
}


public String getUsernameFromToken(String token){
    String username = Jwts
            .parser()
            .setSigningKey(keyWord)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    return username;
}


}
