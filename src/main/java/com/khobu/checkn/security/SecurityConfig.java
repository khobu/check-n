package com.khobu.checkn.security;


import com.khobu.checkn.error.AccessDenied;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{


	@Autowired
    private AccessDenied accessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler;

    private SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();

	@Autowired
    private JdbcTemplate jdbcTemplate;
	// Authentication : User --> Roles

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




    @Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {

		auth.jdbcAuthentication().dataSource(jdbcTemplate.getDataSource())
        .usersByUsernameQuery(
            "select username,password,active from credential where username=?")
        .authoritiesByUsernameQuery(
            "select username, role from user where username=?")
        .passwordEncoder(passwordEncoder());


		/* auth.inMemoryAuthentication()
			 .passwordEncoder(passwordEncoder())
            .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
            .and()
            .withUser("user").password(passwordEncoder().encode("user")).roles("USER");*/

	}

	 @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
            .anonymous()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler)
            .authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/test*").permitAll()
            .antMatchers("/login*").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .and()
            .formLogin()
            .successHandler(mySuccessHandler)
            .failureHandler(myFailureHandler)
            .and()
            .httpBasic()
            .and()
            .logout();
    }

}

// 
