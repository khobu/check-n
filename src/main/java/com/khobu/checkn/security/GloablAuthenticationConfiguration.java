package com.khobu.checkn.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class GloablAuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(jdbcTemplate.getDataSource())
        .usersByUsernameQuery(
            "select username,password,active from credential where username=?")
        .authoritiesByUsernameQuery(
            "select username, role from user where username=?")
        .passwordEncoder(new BCryptPasswordEncoder());
	}

}
