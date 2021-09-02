package ru.krylov.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krylov.web.dao.UserDAO;
import ru.krylov.web.model.User;
import ru.krylov.web.service.UserService;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserDAO userDAO;

	@Autowired
	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	@Transactional (readOnly = true)
	public List<User> allUsers() {
		return userDAO.allUsers();
	}

	@Override
	public void add(User user) {
		userDAO.add(user);
	}

	@Override
	public void delete(int id) {
		userDAO.delete(id);
	}

	@Override
	public void edit(User user) {
		userDAO.edit(user);
	}

	@Override
	@Transactional (readOnly = true)
	public User getById(int id) {
		return userDAO.getById(id);
	}

	@Override
	@Transactional (readOnly = true)
	public User getByUsername(String username) {
		return userDAO.getByUsername(username);
	};

}
