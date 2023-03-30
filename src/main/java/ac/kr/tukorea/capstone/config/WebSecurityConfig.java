package ac.kr.tukorea.capstone.config;

import ac.kr.tukorea.capstone.config.auth.UserDetailsImplService;
import ac.kr.tukorea.capstone.config.auth.jwt.JwtAuthenticationFilter;
import ac.kr.tukorea.capstone.config.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()

                .httpBasic().disable()
                //.formLogin().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter)

                .authorizeRequests()
                .antMatchers("api/v1/user/register/**").permitAll()
                .antMatchers("api/v1/user/login/**").permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(new JwtTokenProvider(new UserDetailsImplService(u))), UsernamePasswordAuthenticationFilter.class);
    }
}
