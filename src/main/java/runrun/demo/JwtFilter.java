package runrun.demo;
import jakarta.servlet.ServletException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import runrun.demo.jwt.JwtUtil;
import runrun.demo.service.RedisService;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    public JwtFilter(JwtUtil jwtUtil, RedisService redisService) {
        this.jwtUtil = jwtUtil;
        this.redisService = redisService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); //Bearer 제거
            if ( jwtUtil.validationToken(token)) {

                if (redisService.isBlackListed(token)) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return;
                }

                String username = jwtUtil.getUsername(token);

                // Spring Security 인증 등록

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, null);

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // 요청한 사람의 IP, 세션Id 포함

                SecurityContextHolder.getContext().setAuthentication(auth); // 인증된 사용자임을 등록

            }
        }
        filterChain.doFilter(request,response);
    }
}
