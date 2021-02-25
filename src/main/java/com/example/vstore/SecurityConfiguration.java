package com.example.vstore;

import com.example.vstore.bind.UserServer;
import com.example.vstore.bind.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder)
        .and()
                .jdbcAuthentication().dataSource(dataSource)
                //query check thong tin tai khoan
                .usersByUsernameQuery("select user_name,password,enabled from user where user_name = ?")
                //query check role user
                .authoritiesByUsernameQuery("select user_name, role from user where user_name = ?");

    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // tat csrf, vi de mac dinh se khong dung duoc POST
                .authorizeRequests()
                    .antMatchers("/login", "/sign-up").permitAll()  //chap nhan tat ca, ke ca chua dang nhap
                    .antMatchers("/admin/**").hasRole("ADMIN")      // chi admin co quyen try cap
                    .anyRequest().authenticated()       // tat ca cac endpoints khac deu phai dang nhap
                .and()
                .formLogin()
                    .loginPage("/login")    // dia chi form login
                    .loginProcessingUrl("/login")   // request chua userName+password de login
                    .usernameParameter("userName")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/home", true)
                    .failureUrl("/login?error=Dang nhap that bai, hay dang nhap lai!")
                .and()
                .exceptionHandling()
                    .accessDeniedPage("/login?error=Truy cap bi tu choi!")
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?error=Dang xuat thanh cong!");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }


}
