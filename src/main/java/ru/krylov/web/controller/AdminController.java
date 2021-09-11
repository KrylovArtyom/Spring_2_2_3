package ru.krylov.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.krylov.web.model.Role;
import ru.krylov.web.model.User;
import ru.krylov.web.service.RoleService;
import ru.krylov.web.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping("/allUsers")
	public String getAllUsers(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("admin", userService.getByUsername(username));
		model.addAttribute("users", userService.allUsers());
		model.addAttribute("allRoles", roleService.allRoles());
		return "admin/allUsers";
	}



	@PostMapping("/newUser")
	public String addUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult,
						  @RequestParam(value = "roleId", required = false) Integer[] roleId) {
		if (bindingResult.hasErrors()) {
			return "admin/allUsers";
		}
		if (roleId != null) {
			for (Integer i : roleId) {
				user.addRole(roleService.getRoleById(i));
			}
		} else {
			user.addRole(roleService.getDefaultRole());
		}
		userService.add(user);
		return "redirect:/admin/allUsers";
	}



	@GetMapping ("/userEdit/{id}")
	public String editUser(@PathVariable("id") int id,
						   Model model) {

		model.addAttribute("user", userService.getById(id));
		model.addAttribute("allRoles", roleService.allRoles());

		return "redirect:/admin/allUsers";
	}

	@PatchMapping ("/userEdit/{id}")
	public String editUser(@ModelAttribute("user") @Valid User user,
							 BindingResult bindingResult,
							 @RequestParam(value = "roleId", required = false) Integer[] roleId) {
		if (bindingResult.hasErrors()) {
			return "admin/userEdit/{id}";
		}
		if (roleId != null) {
			for (Integer i : roleId) {
				user.addRole(roleService.getRoleById(i));
			}
		}
		userService.edit(user);
		return "redirect:/admin/allUsers";
	}

	@DeleteMapping ("/delete/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		userService.delete(id);
		return "redirect:/admin/allUsers";
	}
}
