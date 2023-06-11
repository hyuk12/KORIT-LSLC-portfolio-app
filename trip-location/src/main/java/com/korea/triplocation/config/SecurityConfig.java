package com.korea.triplocation.config;

import com.korea.triplocation.security.jwt.JwtAuthenticationEntryPoint;
import com.korea.triplocation.security.jwt.JwtAuthenticationFilter;
import com.korea.triplocation.security.jwt.JwtTokenProvider;
import com.korea.triplocation.security.oauth2.OAuth2SuccessHandler;
import com.korea.triplocation.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.httpBasic().disable();
        http.formLogin().disable();

        http.authorizeRequests()
                .antMatchers("/api/v1/auth/**", "/image/**", "/post/**", "/api/v1/review/list/**", "/api/v1/user/search", "/api/v1/travel/plan/info/review", "/locations/popular")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .oauth2Login()
                .loginPage("http://hyuk12.s3-website.ap-northeast-2.amazonaws.com/auth/login")
                .successHandler(oAuth2SuccessHandler)
                .userInfoEndpoint()
                .userService(authService);

    }
}
