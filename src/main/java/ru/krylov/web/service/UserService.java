package ru.krylov.web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.krylov.web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
	List<User> allUsers();
	void add(User user);
	void delete(int id);
	void edit(User user);
	User getById(int id);
}
