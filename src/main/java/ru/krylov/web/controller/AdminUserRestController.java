package ru.krylov.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.krylov.web.model.User;
import ru.krylov.web.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserRestController {

	final private UserService userService;

	public AdminUserRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<User>> getAll() {
		return new ResponseEntity<>(userService.allUsers(), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUser(@PathVariable Integer userId) {

		if (userId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		User user = userService.getById(userId);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(userService.getById(userId), HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Void> addUser(@RequestBody @Valid User user) {

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		userService.add(user);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{userId}/edit")
	public ResponseEntity<Void> updateUser(@RequestBody @Valid User user) {

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		userService.edit(user);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{userId}/delete")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {

		if (userId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		User user = userService.getById(userId);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		userService.delete(userId);
		return ResponseEntity.ok().build();
	}
}
