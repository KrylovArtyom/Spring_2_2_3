package ru.krylov.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.krylov.web.model.User;
import ru.krylov.web.service.UserService;

@RestController
@RequestMapping("/user/info")
public class UserRestController {

	final private UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public ResponseEntity<User> getUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<>(userService.getByUsername(username), HttpStatus.OK);
	}
}
