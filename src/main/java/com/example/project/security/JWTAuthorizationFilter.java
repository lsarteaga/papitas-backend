package com.example.project.security;

import com.example.project.service.UserService;
import com.example.project.utility.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.project.security.Constants.HEADER_AUTHORIZATION_KEY;
import static com.example.project.security.Constants.TOKEN_BEARER_PREFIX;

// clase que controla la autoriacion a los recuros (rutas protegidas)
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader(HEADER_AUTHORIZATION_KEY);
        // si el token esta incorrecto se dispara la ecepcion
        if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith(TOKEN_BEARER_PREFIX)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        /* caso contrario el token se lo decodifica
        se verifica que corresponda al usuario que lo esta enviando
        de ser asi, pasa el filtro y es autorisado al recurso que haya solicitado
         */
        final String token =authorizationHeader.replace(TOKEN_BEARER_PREFIX + " ", "");
        String userName = TokenProvider.getUserName(token);
        UserDetails user = userService.loadUserByUsername(userName);
        UsernamePasswordAuthenticationToken authenticationToken = TokenProvider.getAuthentication(token, user);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
