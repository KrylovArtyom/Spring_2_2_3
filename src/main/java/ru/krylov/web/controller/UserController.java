package ru.krylov.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krylov.web.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String getUserPage(Model model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("user", userService.getByUsername(username));
		return "user/user";
	}
}
