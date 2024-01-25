package spring.project.sec;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public DataSource dataSource() {
      return new EmbeddedDatabaseBuilder()
          .setType(EmbeddedDatabaseType.H2)
          .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
          .build();
	}


	/* Utilisation de la source de données JDBC pour stocker
	 * les informations sur les utilisateurs */
	@Bean
	public JdbcUserDetailsManager createUsers(DataSource dataSource) {
		UserDetails user = User.builder()
				.username("user")
				.password(getPasswordEncoder().encode("user"))
				.roles("USER")
				.build();
		UserDetails admin = User.builder()
				.username("admin")
				.password(getPasswordEncoder().encode("admin"))
				.roles("USER","ADMIN")
				.build();

		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);
		return jdbcUserDetailsManager;
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


		// Ajouter une page personnalisée pour indiquer un accès non authorisé
		http.csrf(csrf->csrf.ignoringRequestMatchers("/h2-console/**"));
		http.headers(header-> header.frameOptions().sameOrigin());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/admin/*").hasRole("ADMIN")
				.requestMatchers("/user/*").hasAnyRole("ADMIN", "USER")
				.requestMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated());
		http.formLogin(loginConfig -> loginConfig.loginPage("/login").permitAll());
		http.exceptionHandling(eh -> eh.accessDeniedPage("/403"));
		return http.build();
	}


}