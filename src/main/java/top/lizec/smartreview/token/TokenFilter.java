package top.lizec.smartreview.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.lizec.smartreview.entity.User;
import top.lizec.smartreview.entity.UserDetail;
import top.lizec.smartreview.utils.TokenUtils;

@Component
public class TokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationKey = "Authorization";
        String authorizationValue;
        try {
            authorizationValue = request.getHeader(authorizationKey);
        } catch (Exception e) {
            authorizationValue = null;
        }
        // Token 开头部分 默认 Bearer 开头
        String bearer = "Bearer ";
        if (authorizationValue != null && authorizationValue.startsWith(bearer)) {
            // token
            String token = authorizationValue.substring(bearer.length());
            User user = TokenUtils.validationToken(token);
            if (user != null) {
                UserDetails userDetails = new UserDetail(user);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(userDetails.getUsername());
                // 授权
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
