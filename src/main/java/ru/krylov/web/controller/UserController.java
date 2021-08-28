package ru.krylov.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.krylov.web.model.User;
import ru.krylov.web.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping ("/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String users(Model model) {
		model.addAttribute("users", userService.allUsers());
		return "users/users";
	}

	@GetMapping ("/newUser")
	public String newUser(@ModelAttribute("user") User user) {
		return "users/newUser";
	}

	@PostMapping
	public String createUser(@ModelAttribute("user") @Valid User user,
							 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "users/newUser";
		}
		userService.add(user);
		return "redirect:/users";
	}

	@GetMapping ("/{id}/editUser")
	public String editUser(@PathVariable("id") int id, Model model) {
		model.addAttribute("user", userService.getById(id));
		return "users/editUser";
	}

	@PatchMapping ("/{id}")
	public String updateUser(@ModelAttribute("user") @Valid User user,
							 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "users/editUser";
		}
		userService.edit(user);
		return "redirect:/users";
	}

	@DeleteMapping ("/{id}")
	public String delete(@PathVariable("id") int id) {
		userService.delete(id);
		return "redirect:/users";
	}
}
