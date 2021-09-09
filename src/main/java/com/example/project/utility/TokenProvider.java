package com.example.project.utility;
import static com.example.project.security.Constants.*;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

// clase que gestiona la firma, el cifrado y descifrado de los tokens
public class TokenProvider {

    public TokenProvider() {
    }
    /* se genera el token segun los roles que posea el usuario (ADMIN - USER)
    se cifra con el algoritmo hs256, y se firma con la clave secreta de la aplicacion
    se le agrega un tiempo de vida util
     */
    public static String generateToken(Authentication authentication) {
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256,SUPER_SECRET_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(ISSUER_TOKEN)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .compact();
    }

    /* se decodifica el token para verificar si es del usuario y posee los permisos
    para acceder al recurso
     */
    public static UsernamePasswordAuthenticationToken getAuthentication(final String token,
                                                                        final UserDetails userDetails) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey(SUPER_SECRET_KEY);
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        final Claims claims = claimsJws.getBody();
        final Collection<SimpleGrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    // obtiene el username
    public static String getUserName(final String token) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey(SUPER_SECRET_KEY);
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }
}
