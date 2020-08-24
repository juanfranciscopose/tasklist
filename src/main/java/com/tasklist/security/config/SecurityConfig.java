package com.tasklist.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tasklist.security.jwt.JwtEntryPoint;
import com.tasklist.security.jwt.JwtTokenFilter;
import com.tasklist.security.service.imp.MainUserServiceImp;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//for Admin role
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private MainUserServiceImp mainUserService;
	@Autowired
	private JwtEntryPoint jwtEntryPoint;

	@Bean
	public JwtTokenFilter jwtTokenFilter() {
		return new JwtTokenFilter();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.mainUserService).passwordEncoder(this.passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()//form token
			.authorizeRequests()
			.antMatchers("/auth/**").permitAll()//anyone can create and login users
			.anyRequest().authenticated()//for the rest of endpoints have to be authenticated
			.and()
			.exceptionHandling().authenticationEntryPoint(jwtEntryPoint)//throws error 401
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//session without cookies
		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);//pass the user to the authentication context
	}	
	

}
