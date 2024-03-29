package ac.kr.tukorea.capstone.config;

import ac.kr.tukorea.capstone.config.handler.CustomAccessDeniedHandler;
import ac.kr.tukorea.capstone.config.handler.CustomAuthenticationEntryPoint;
import ac.kr.tukorea.capstone.config.jwt.JwtAuthenticationFilter;
import ac.kr.tukorea.capstone.config.jwt.JwtAuthorizationFilter;
import ac.kr.tukorea.capstone.config.jwt.JwtTokenService;
import ac.kr.tukorea.capstone.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CorsFilter corsFilter;
    private final JwtTokenService jwtTokenProvider;
    private final FcmService fcmService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // swagger
        web.ignoring().antMatchers(
                "/v2/api-docs",  "/configuration/ui",
                "/swagger-resources", "/configuration/security",
                "/swagger-ui.html", "/webjars/**","/swagger/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()

                .httpBasic().disable()
                .formLogin().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                    .addFilter(corsFilter)
                    .addFilterBefore(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider, fcmService), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()

                .authorizeRequests()
                    .antMatchers("/swagger-ui/**").permitAll()
                    .antMatchers("/api/v1/user/register/**", "/api/v1/user/refresh/**").permitAll()
                    .antMatchers("/api/v1/product/img/**", "/api/v1/user/img/**", "/api/v1/post/img/**").permitAll()
                    .antMatchers("/stomp/chat/**").permitAll()
                    .anyRequest().authenticated();
    }
}
