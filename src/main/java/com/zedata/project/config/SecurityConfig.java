package com.zedata.project.config;

import com.zedata.project.service.basicServices.AuthUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote Security配置
 * @since 2024/10/2 2:00
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthUserService authUserService;

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(AuthUserService authUserService, JwtRequestFilter jwtRequestFilter) {
        this.authUserService = authUserService;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    /**
     * 配置密码加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 设置 UserDetailsService
        authProvider.setUserDetailsService(authUserService);
        // 设置 PasswordEncoder
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 主要功能为过滤拦截
     * 这个版本引入JWT 过滤器,同时会对上下文进行更新
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // 禁用 CSRF
                .authorizeHttpRequests(auth -> auth
                        // 放行 oauth 下相关鉴权接口
                        .antMatchers("/oauth/**").permitAll()
                        // 其他请求需认证
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 配置为无状态
                );

        // 添加自定义的 JWT 请求过滤器，在 UsernamePasswordAuthenticationFilter 之前运行
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    /**
     * 主要功能为过滤拦截
     *  这个版本是未引入 JWT
     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // 如果不需要CSRF保护，可以禁用
////                .anonymous().disable() // 禁用匿名用户机制
//                .authorizeRequests(authorize -> authorize
//                        // 登录接口放行
//                        .antMatchers("/api/login").permitAll()
//                        // 注册接口放行
//                        .antMatchers("/api/register").permitAll()
//                        // 验证码相关接口放行
//                        .antMatchers("/api/captcha/**").permitAll()
//                        // 管理接口限制管理员角色
//                        .antMatchers("/api/admin/**").hasRole("admin")
//                        // 限制接口角色
//                        .antMatchers("/api/general/**").hasAnyRole("admin", "user")
//                        // 其他请求需要身份认证
//                        .anyRequest().authenticated()
//                )
////                // 默认表单登录
////                .formLogin(form -> form
////                        .loginPage("/login")
////                        .defaultSuccessUrl("/")
////                        .permitAll()
////                )
//                // 禁用默认的表单登录
//                .formLogin(AbstractHttpConfigurer::disable)
////                .logout(LogoutConfigurer::permitAll) //登出操作
//                //禁用登出操作
//                .logout(LogoutConfigurer::disable)
//                .sessionManagement(session -> session
//                        .maximumSessions(1) // 每个用户只允许一个会话
//                        .maxSessionsPreventsLogin(false) // 当达到最大会话数时，不阻止新的登录
//                        .expiredUrl("/login?expired") // 会话过期后的重定向URL
//                );
//        return http.build();
//    }


}