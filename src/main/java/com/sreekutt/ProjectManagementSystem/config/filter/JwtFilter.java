package com.sreekutt.ProjectManagementSystem.config.filter;

import com.sreekutt.ProjectManagementSystem.service.AppUserDetailsService;
import com.sreekutt.ProjectManagementSystem.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AppUserDetailsService userDetailsService;

    private static final List<String> PUBLIC_URLs = List.of("/api/login","/api/register");


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        if (PUBLIC_URLs.contains(path)){
            filterChain.doFilter(request,response);
            return;
        }

        String jwt = null;
        String email = null;

        //first check auth header
        final String authHeader = request.getHeader("Authorization");
        if (authHeader!=null && authHeader.startsWith("Bearer ") ){
            jwt = authHeader.substring(7);
        }

        //if not in auth header check cookies
        if (jwt == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies){
                    if ("jwt" .equals(cookie.getName()) ) {
                        jwt = cookie.getValue();
                        break;
                    }
                }
            }
        }



        if (jwt != null) {
            email = jwtUtil.extractEmail(jwt);
            if (email != null && SecurityContextHolder.getContext().getAuthentication()==null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (jwtUtil.validateToken(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }
            System.out.println("Extracted email from JWT: " + email);

        }
        filterChain.doFilter(request,response);
    }
}
