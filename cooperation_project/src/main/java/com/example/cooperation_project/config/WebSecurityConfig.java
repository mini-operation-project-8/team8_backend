package com.example.cooperation_project.config;

import com.example.cooperation_project.jwt.JwtAuthFilter;
import com.example.cooperation_project.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(("/chitchat/**")).permitAll()
/*
                .antMatchers("/api/user/login").permitAll() // permitAll() : 모든사람이 사용가능하게 만들어준다. 관리자만, 회원만 접근 가능하게도 만들수 있다. 제일위에는 무엇?
                .antMatchers(HttpMethod.GET,"/api/posts").permitAll() // 모든 유저는 게시글 조회가 가능하게 해야한다.
                .antMatchers(HttpMethod.GET,"/api/post/{id}").permitAll() // 세부적으로 나눠서 모든 유저를 접근 가능하게 만든다. 상세조회 {id}가 맞는지
                .antMatchers(HttpMethod.POST,"/api/user/signup").permitAll() // 회원가입
*/
                .anyRequest().authenticated()
                .and().cors()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //http.formLogin().loginPage("@#$@#$").permitAll();
        http.formLogin().permitAll();
        http.exceptionHandling().accessDeniedPage("/api/user/forbidden");

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration config = new CorsConfiguration();

        // 사전에 약속된 출처를 명시
        //        config.addAllowedOriginPattern("*");
        //config.addAllowedOrigin("http://charleybucket.s3-website.ap-northeast-2.amazonaws.com");
        config.setAllowedOriginPatterns(Arrays.asList("*"));

        // 특정 헤더를 클라이언트 측에서 사용할 수 있게 지정
        // 만약 지정하지 않는다면, Authorization 헤더 내의 토큰 값을 사용할 수 없음
        config.addExposedHeader(JwtUtil.AUTHORIZATION_HEADER);

        // 본 요청에 허용할 HTTP method(예비 요청에 대한 응답 헤더에 추가됨)
        config.addAllowedMethod("*");

        // 본 요청에 허용할 HTTP header(예비 요청에 대한 응답 헤더에 추가됨)
        config.addAllowedHeader("*");

        // 기본적으로 브라우저에서 인증 관련 정보들을 요청 헤더에 담지 않음
        // 이 설정을 통해서 브라우저에서 인증 관련 정보들을 요청 헤더에 담을 수 있도록 해줍니다.
        config.setAllowCredentials(true);

        // allowCredentials 를 true로 하였을 때,
        // allowedOrigin의 값이 * (즉, 모두 허용)이 설정될 수 없도록 검증합니다.
        config.validateAllowCredentials();

        // 어떤 경로에 이 설정을 적용할 지 명시합니다. (여기서는 전체 경로)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
