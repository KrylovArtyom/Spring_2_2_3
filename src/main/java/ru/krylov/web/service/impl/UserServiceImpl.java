package ru.krylov.web.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krylov.web.dao.UserDAO;
import ru.krylov.web.model.Role;
import ru.krylov.web.model.User;
import ru.krylov.web.service.UserService;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserDAO userDAO;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
		this.userDAO = userDAO;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> allUsers() {
		return userDAO.allUsers();
	}

	@Override
	@Transactional
	public void add(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAO.add(user);
	}

	@Override
	@Transactional
	public void delete(int id) {
		userDAO.delete(id);
	}

	@Override
	@Transactional
	public void edit(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAO.edit(user);
	}

	@Override
	@Transactional(readOnly = true)
	public User getById(int id) {
		return userDAO.getById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public User getByUsername(String username) {
		return userDAO.getByUsername(username);
	}

	@Override
	@Transactional
	public void changeUserRoles(int id, Integer[] roleId) {
		userDAO.changeUserRoles(id, roleId);
	}
}
