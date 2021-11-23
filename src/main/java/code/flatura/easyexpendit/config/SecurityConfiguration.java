package code.flatura.easyexpendit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    UserSecurityService userDetailsService;

    @Autowired
    public SecurityConfiguration(UserSecurityService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/categories**").hasRole("ADMIN")
                //.antMatchers("/users**").hasRole("ADMIN")
                //.antMatchers("/transactions**").hasRole("ADMIN")
                //.antMatchers("/roles**").hasRole("ADMIN")
                //.antMatchers("/consumables**").hasAnyRole("USER","ADMIN")
                //.antMatchers("/anonymous**").anonymous()
                .antMatchers("/login**").permitAll()
                //.antMatchers("/dist/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                //.loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/", true).failureUrl("/login.html?error=true")
                //.failureHandler(authenticationFailureHandler())
                .and().logout().logoutUrl("/logout").deleteCookies("JSESSIONID");
        //.logoutSuccessHandler(logoutSuccessHandler());
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
