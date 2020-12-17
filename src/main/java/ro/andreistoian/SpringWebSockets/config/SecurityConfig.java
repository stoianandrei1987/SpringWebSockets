package ro.andreistoian.SpringWebSockets.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthFailureHandler authFailureHandler;

    @Autowired
    AuthSuccessHandler authSuccessHandler;

    @Autowired
    LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/chat/**").permitAll()
                .antMatchers("/app/**").permitAll()
                .and()
                .formLogin().permitAll()
                .loginProcessingUrl("/login")
                .usernameParameter("user-name")
                .passwordParameter("user-pass")
                .failureHandler(authFailureHandler)
                .successHandler(authSuccessHandler)
                .permitAll(true)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //  .logoutSuccessHandler(logoutSuccessHandler)
                .logoutSuccessUrl("/")
                .deleteCookies("remember-me", "JSESSIONID")
                .permitAll().and()
                .headers().frameOptions().sameOrigin().and()
                .rememberMe();
    }


}
