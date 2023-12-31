package med.voll.api.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import med.voll.api.model.Usuario;

@Service
public class TokenService {
	
	@Value("${api.security.secret}")
	private String apiSecret;

	public String generarToken(Usuario usuario) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(apiSecret); 
		    return JWT.create()
		        .withIssuer("voll med")
		        .withSubject(usuario.getLogin())
		        .withClaim("id", usuario.getId())
		        .withExpiresAt(generarFechaExpiracion())
		        .sign(algorithm);
		} catch (JWTCreationException exception){
			throw new RuntimeException("error al generar el  token jwt", exception);
		}
	}
	
	public String getSubject(String token) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(apiSecret);
		    return JWT.require(algorithm)
		        .withIssuer("voll med")
		        .build()
		        .verify(token)
		        .getSubject();
		} catch (JWTVerificationException exception){
			throw new RuntimeException("Token Invalido o expirado!");
		}
	}
	
	private Instant generarFechaExpiracion() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
	}
}
