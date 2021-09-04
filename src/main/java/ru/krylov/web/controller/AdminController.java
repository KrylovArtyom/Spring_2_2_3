package ru.krylov.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", userService.allUsers());
		return "admin/users";
	}

	@GetMapping ("/newUser")
	public String newUser(@ModelAttribute("user") User user,
						  Model model) {
		List<Role> roles = roleService.allRoles();
		model.addAttribute("allRoles", roles);
		return "admin/newUser";
	}

	@PostMapping("/users")
	public String createUser(@ModelAttribute("user") @Valid User user,
							 BindingResult bindingResult,
							 @RequestParam(value = "index", required = false) Integer[] index,
							 Model model) {
		model.addAttribute("allRoles", roleService.allRoles());
		if (bindingResult.hasErrors()) {
			return "admin/newUser";
		}
		if (index != null) {
			for (Integer i : index) {
				user.addRole(roleService.getRoleById(i));
			}
		}
		userService.add(user);
		return "redirect:/admin/users";
	}

	@GetMapping ("{id}/editUser")
	public String editUser(@PathVariable("id") int id,
						   Model model) {
		List<Role> blabla = roleService.allRoles();
		User hyuzer = userService.getById(id);
		model.addAttribute("user", hyuzer);
		Set<Role> blyablya = hyuzer.getRoles();
		blyablya.forEach(x-> System.out.println(x.getName()));
		model.addAttribute("allRoles", blabla);
		blabla.forEach(x-> System.out.println(x.getName()));
		return "admin/editUser";
	}

	@PatchMapping ("{id}")
	public String updateUser(@ModelAttribute("user") @Valid User user,
							 BindingResult bindingResult,
							 @RequestParam(value = "index", required = false) Integer[] index) {
		if (bindingResult.hasErrors()) {
			return "admin/editUser";
		}
		if (index != null) {
			for (Integer i : index) {
				user.addRole(roleService.getRoleById(i));
			}
		}
		userService.edit(user);
		return "redirect:/admin/users";
	}

	@DeleteMapping ("/{id}")
	public String delete(@PathVariable("id") int id) {
		userService.delete(id);
		return "redirect:/admin/users";
	}
}
