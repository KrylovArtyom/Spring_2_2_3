package ru.krylov.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.krylov.web.model.User;
import ru.krylov.web.service.RoleService;
import ru.krylov.web.service.UserService;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping("")
	public String getAllUsers(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("admin", userService.getByUsername(username));
		model.addAttribute("users", userService.allUsers());
		model.addAttribute("allRoles", roleService.allRoles());
		return "admin/allUsers";
	}

	@PostMapping("/addUser")
	public String addUser(@ModelAttribute("addUser") @Valid User user, BindingResult bindingResult,
						  @RequestParam(value = "roleId", required = false) Integer[] roleId) {
		if (roleId != null) {
			for (Integer i : roleId) {
				user.addRole(roleService.getRoleById(i));
			}
		} else {
			user.addRole(roleService.getDefaultRole());
		}
		userService.add(user);
		return "redirect:/admin";
	}

	@GetMapping("/get")
	@ResponseBody
	public User getUser(int id) {
		return userService.getById(id);
	}

	@PutMapping ("/update")
	public String updateUser(@ModelAttribute("user") @Valid User user,
							 BindingResult bindingResult,
							 @RequestParam(value = "roleId", required = false) Integer[] roleId) {

		if (roleId != null) {
			for (Integer i : roleId) {
				user.addRole(roleService.getRoleById(i));
			}
		} else {
			user.addRole(roleService.getDefaultRole());
		}
		userService.edit(user);
		return "redirect:/admin";
	}


	@DeleteMapping ("/delete")
	public String deleteUser(int id) {
		userService.delete(id);
		return "redirect:/admin";
	}
}
