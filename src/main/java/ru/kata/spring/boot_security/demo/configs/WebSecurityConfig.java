package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // включаем безопастности с помощью этой аннотацией
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserDetailsService userService;
    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetailsService userService) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// набор фильтров
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/user").hasAnyRole("ADMIN", "USER") // права доступа
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated() // всех отправляет на страницу авторизации
                .and()// сам спринг создал нам страницу
                .formLogin().successHandler(successUserHandler)// сам создал страницу
                .permitAll()
                .and()
                .logout()// будет кнопка выйти с сайта
                .permitAll(); // у всех есть доступ к этой штуке
    }
    @Bean
    public PasswordEncoder passwordEncoder() { // не шифруем пароль никак
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        auth.authenticationProvider(provider);
    }
    // аутентификация inMemory
   // @Bean

  //  @Override
    //public UserDetailsService userDetailsService() {
      //  UserDetails user =
             //   User.withDefaultPasswordEncoder()
                      //  .username("user")
                       // .password("user")
                       // .roles("USER")
                       // .build();

       // return new InMemoryUserDetailsManager(user);
   // }
}