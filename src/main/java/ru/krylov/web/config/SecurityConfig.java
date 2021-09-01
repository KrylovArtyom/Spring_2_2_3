package ru.krylov.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.krylov.web.config.handler.LoginSuccessHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final LoginSuccessHandler loginSuccessHandler;

	public SecurityConfig(UserDetailsService userDetailsService,
						  LoginSuccessHandler loginSuccessHandler) {
		this.loginSuccessHandler = loginSuccessHandler;
		this.userDetailsService = userDetailsService;
	}
	@Bean
	protected DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				// указываем страницу с формой логина
				.loginPage("/users/login")
				//указываем логику обработки при логине
				.successHandler(loginSuccessHandler)
				// указываем action с формы логина
				.loginProcessingUrl("/login")
				// Указываем параметры логина и пароля с формы логина
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				// даем доступ к форме логина всем
				.permitAll();

		http.logout()
				// разрешаем делать логаут всем
				.permitAll()
				// указываем URL логаута
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				// указываем URL при удачном логауте
				.logoutSuccessUrl("/users/login?logout")
				//выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
				.and().csrf().disable();

		http
				// делаем страницу регистрации недоступной для авторизированных пользователей
				.authorizeRequests()
				//страницы аутентификаци доступна всем
				.antMatchers("/login").anonymous();
				// защищенные URL
				//.antMatchers("/users").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')").anyRequest().authenticated();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
