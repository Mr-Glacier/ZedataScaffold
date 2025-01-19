package com.zedata.project.config;

import com.zedata.project.service.basicServices.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote JWT过滤器
 * @since 2025/1/20 5:52
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthUserService authUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String jwtStr = null;
        Map<String, Object> claims = null;

        if (authorizationHeader != null) {
            jwtStr = authorizationHeader;
            claims = jwtUtil.getUsernameFromToken(jwtStr);
        }
        if (claims == null) {
            throw new RuntimeException("token无效");
        }
        String account = claims.get("account").toString();

        // 表明当前用户信息还没有被验证
        if (account != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 使用 security 的用户信息进行验证
            UserDetails userDetails = this.authUserService.loadUserByUsername(account);

            // 进行一遍 token 的验证
            if (jwtUtil.validateToken(jwtStr)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // 这是更新上下文认证信息
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}