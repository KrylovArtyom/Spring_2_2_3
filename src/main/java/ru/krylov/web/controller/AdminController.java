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
		model.addAttribute("user", userService.getByUsername(username));

		model.addAttribute("users", userService.allUsers());
		return "admin/allUsers";
	}

	@GetMapping ("/addUser")
	public String addUser(@ModelAttribute("user") User user,
						  Model model) {
		List<Role> roles = roleService.allRoles();
		model.addAttribute("allRoles", roles);
		return "admin/addUser";
	}

	@PostMapping("/addUser")
	public String addUser(@ModelAttribute("user") @Valid User user,
							 BindingResult bindingResult,
							 @RequestParam(value = "roleId", required = false) Integer[] roleId,
							 Model model) {
		model.addAttribute("allRoles", roleService.allRoles());

		if (bindingResult.hasErrors()) {
			return "admin/addUser";
		}
		if (roleId != null) {
			for (Integer i : roleId) {
				user.addRole(roleService.getRoleById(i));
			}
		}
		userService.add(user);
		return "redirect:/admin/allUsers";
	}

	@GetMapping ("/editUser/{id}")
	public String editUser(@PathVariable("id") int id,
						   Model model) {

		model.addAttribute("user", userService.getById(id));
		model.addAttribute("allRoles", roleService.allRoles());

		return "admin/editUser";
	}

	@PatchMapping ("/editUser/{id}")
	public String editUser(@ModelAttribute("user") @Valid User user,
							 BindingResult bindingResult,
							 @RequestParam(value = "roleId", required = false) Integer[] roleId) {
		if (bindingResult.hasErrors()) {
			return "admin/editUser";
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
