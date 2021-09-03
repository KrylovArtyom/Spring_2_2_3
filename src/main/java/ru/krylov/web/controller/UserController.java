package ru.krylov.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krylov.web.service.UserService;


@Controller
@RequestMapping
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping ("/{name}")
	public String newUser(@PathVariable("name") String name, Model model) {
		model.addAttribute("user", userService.getByUsername(name));
		return "users/user";
	}


}
