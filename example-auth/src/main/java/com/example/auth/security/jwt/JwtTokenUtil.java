package com.example.auth.security.jwt;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.auth.exceptions.InvalidTokenException;
import com.example.auth.models.entites.User;

import io.jsonwebtoken.*;


@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID 			= 7830383971674546383L;
														
	public static final long JWT_TOKEN_VALIDITY 		= 1 * 60 * 60;

	public static final long JWT_REFRESH_TOKEN_VALIDITY = 720 * 60 * 60;
	
	@Value("${jwt.secret}")
	private String secret;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Boolean isAccessToken(String token) {
		final Claims claims = getValidateAndAllClaimsFromToken(token);	
		return  (Boolean) claims.get("accessToken");
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getValidateAndAllClaimsFromToken(token);
 		return claimsResolver.apply(claims);
	}
	
	private Claims getValidateAndAllClaimsFromToken(String token) {
        try {
            
        	return Jwts.parser()
            			.setSigningKey(secret)
            			.parseClaimsJws(token)
            			.getBody();
            
        } catch (SignatureException ex) {
        	throw new InvalidTokenException("Firma no valida");
        } catch (MalformedJwtException ex) {
        	throw new InvalidTokenException("Token mal formado");
        } catch (ExpiredJwtException ex) {
        	throw new InvalidTokenException("Token expirado");
        } catch (UnsupportedJwtException ex) {
        	throw new InvalidTokenException("Formato del token no soportado");
        } catch (IllegalArgumentException ex) {
        	throw new InvalidTokenException("Los argumentos del token estan vacios");
        }
        
    }
	
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(User userDetails, String login) {
		Map<String, Object> claims = new HashMap<>();
		List<String> roles  =  userDetails.getRoles().stream().map(rol ->  rol.getName().name() ).collect(Collectors.toList());		
		claims.put("roles", roles);
		claims.put("accessToken", true);
		return doGenerateToken(claims, login, JWT_TOKEN_VALIDITY);
	}

	public String generateRefreshToken(String login) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("accessToken", false);
		return this.doGenerateToken(claims, login,  JWT_REFRESH_TOKEN_VALIDITY);
	}
	
	private String doGenerateToken(Map<String, Object> claims, String subject, Long time) {		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + time * 1000 )) 
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean isToken(String auth) {
		return StringUtils.hasText(auth) && auth.startsWith("Bearer ");
	}
	
	public String getToken(String auth) {
		return  auth.substring(7, auth.length());
	}
		
}

