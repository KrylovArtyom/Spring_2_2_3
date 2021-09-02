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
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;

	@Autowired
	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", userService.allUsers());
		return "admin/users";
	}

	@GetMapping ("/newUser")
	public String newUser(@ModelAttribute("user") User user) {
		return "admin/newUser";
	}

	@PostMapping("/users")
	public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "admin/newUser";
		}
		userService.add(user);
		return "redirect:/admin/users";
	}

	@GetMapping ("{id}/editUser")
	public String editUser(@PathVariable("id") int id, Model model) {
		model.addAttribute("user", userService.getById(id));
		return "admin/editUser";
	}

	@PatchMapping ("/{id}")
	public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "admin/editUser";
		}
		userService.edit(user);
		return "redirect:admin/users";
	}

	@DeleteMapping ("/{id}")
	public String delete(@PathVariable("id") int id) {
		userService.delete(id);
		return "redirect:admin/users";
	}
}
