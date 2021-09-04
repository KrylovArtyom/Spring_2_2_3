package ru.krylov.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.krylov.web.dao.UserDAO;
import ru.krylov.web.model.User;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserDAO userDAO;

	@Autowired
	public UserDetailsServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userDAO.getByUsername(s);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}
}
