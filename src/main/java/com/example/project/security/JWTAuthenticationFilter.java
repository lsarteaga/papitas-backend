package com.example.project.security;

import static com.example.project.security.Constants.HEADER_AUTHORIZATION_KEY;
import static com.example.project.security.Constants.TOKEN_BEARER_PREFIX;

import com.example.project.model.UserModel;
import com.example.project.utility.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// esta clase maneja la autenticacion
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
        try {
            /*
            si las credenciales del usuario no corresponden a las de la BBDD
            se dispara una ecepcion, caso contrario el metodo successfulAuthentication se ejecuta
            y redirije al recurso solicitado
             */
            UserModel credentials = new ObjectMapper().readValue(request.getInputStream(), UserModel.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(), credentials.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) throws IOException, ServletException {
        /*
        se genera un token de autoriacion, este es firmado por la aplicacion
        se manda al cliente en el header de la respuesta
        el cliente usara este token para posteriormente acceder a las rutas protegidas
         */
        String token = TokenProvider.generateToken(auth);
        response.addHeader(HEADER_AUTHORIZATION_KEY, TOKEN_BEARER_PREFIX + " " + token);
    }
}
