package org.sayem.librarymanagementsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LibraryManagementSystemApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String checkUserSql = "SELECT COUNT(*) FROM users WHERE email = 'admin@gmail.com'";
		Integer count = jdbcTemplate.queryForObject(checkUserSql, Integer.class);

		if (count == null || count == 0) {
			String insertUserSql = "INSERT INTO users (username, email, first_name, last_name, password, is_active, is_enabled, role) " +
					"VALUES ('admin@gmail.com', 'admin@gmail.com', 'sayed', 'sayem', '$2a$10$1jUdF2GVrW2mHIwzK89rAuqyM3l3lbr3tyn5BtTVVPtso6gZBGm5.', TRUE, TRUE, 'ROLE_ADMIN')";
			jdbcTemplate.execute(insertUserSql);
		}
	}

}
