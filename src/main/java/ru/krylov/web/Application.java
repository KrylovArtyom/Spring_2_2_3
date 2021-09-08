package ru.krylov.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.krylov.web.dao.RoleDAO;
import ru.krylov.web.dao.UserDAO;

@SpringBootApplication
@ComponentScan(basePackages="ru.krylov.web.config")
@Import({UserDAO.class, RoleDAO.class})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
