package ru.krylov.web.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "roles")
public class Role implements GrantedAuthority {

	@Id
	private int id;

	private String name;

	@Transient
	@ManyToMany(mappedBy = "roles")
	private Set<User> user = new HashSet<>();

	public Role() {
	}

	public Role(int id) {
		this.id = id;
	}

	public Role(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<User> getUser() {
		return user;
	}
	public void setUser(Set<User> user) {
		this.user = user;
	}

	@Override
	public String getAuthority() {
		return getName();
	}

	@Override
	public int hashCode() {

		int result = 37;
		result = 37 * result + id;
		result = 37 * result + ((name == null) ? 0 : name.hashCode());

		return result;
	}
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Role other = (Role) obj;

		return ((id == other.id) && name.equals(other.name));
	}
	@Override
	public String toString() {

		return "Role [" +
				"id: " + id +
				", name: " + name +
				']';
	}
}
