package br.com.mythkrouz.MK.security;


import br.com.mythkrouz.MK.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.token.secret}")
    String secret;

    public String generateToken(User user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("mythkrouz")
                    .withSubject(user.getEmail())
                    .withExpiresAt(
                            LocalDateTime.now()
                                    .plusHours(3)
                                    .toInstant(ZoneOffset.of("-03:00"))
                    )
                    .sign(algorithm);

            return token;

        }catch (JWTCreationException e){
            throw new RuntimeException("Falha na geração do token JWT", e);
        }
    }

    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("mythkrouz")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){
            return "Token invalido";
        }
    }
}
