package com.paramita.bookStore.security.config;

import com.paramita.bookStore.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerReq = request.getHeader("Authorization");
        if(headerReq==null || !headerReq.startsWith("Bearer ")){
            log.info("Token is not provided");
            filterChain.doFilter(request,response);
            return;
        }
        String jwtToken = headerReq.split("Bearer ")[1];
        String usenameFromToken = jwtService.extractUsernameFromToken(jwtToken);
        UserDetails user = userDetailsService.loadUserByUsername(usenameFromToken);
        if(jwtService.isValid(jwtToken, user.getUsername())){
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
        }
    }
}
