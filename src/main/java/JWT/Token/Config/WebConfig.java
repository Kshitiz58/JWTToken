package JWT.Token.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import JWT.Token.Service.IUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebConfig {
	
	@Autowired
	private IUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer:: disable)
				.authorizeHttpRequests(app -> {
					app.requestMatchers("/css/**","/error/**").permitAll();
					app.requestMatchers("/home","/signup").permitAll();
					app.requestMatchers("/admin/**").hasRole("ADMIN");
					app.requestMatchers("/user/**").hasRole("USER");
					app.anyRequest().authenticated();
				})
				.formLogin(form -> {
					form
					.loginPage("/login")
					.successHandler(new AuthSuccessHandler())
					.permitAll();
				})
				.build();

	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails normalUser = User.builder()
//				.username("hari")
//				.password(passwordEncoder().encode("123"))
//				.roles("USER")
//				.build();
//		UserDetails adminUser = User.builder()
//				.username("ram")
//				.password(passwordEncoder().encode("123"))
//				.roles("ADMIN","USER")
//				.build();
//		return new InMemoryUserDetailsManager(normalUser, adminUser);
//	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
}
