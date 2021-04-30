package uz.pdp.program_47.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.program_47.service.MyAuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
@Autowired
JwtProvider jwtProvider;
@Autowired
    MyAuthService myAuthService;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
String token = request.getHeader("Authorization");
if (token!=null && token.startsWith("Bearer")){
    token = token.substring(7);
    boolean checkToken = jwtProvider.validateToken(token);
    if (checkToken){
        String username = jwtProvider.getUsernameFromToken(token);
        UserDetails userDetails = myAuthService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
filterChain.doFilter(request, response);
    }
}
