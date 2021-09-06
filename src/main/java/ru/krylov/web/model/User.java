package ru.krylov.web.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 2, max = 50, message = "Username should be between 2 and 50 characters")
	@NotNull
	@Column (nullable = false, unique = true, length = 50)
	private String username;

	@NotNull
	@Size(min = 2, max = 60, message = "Password should be between 2 and 50 characters")
	@Column (nullable = false, length = 60)
	private String password;

	@Transient
	private String passwordConfirm;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "Name should not be empty")
	@Size(min = 1, max = 50, message = "Name should be between 1 and 50 characters")
	private String name;

	@Column(nullable = false)
	@NotNull
	@Min(value = 1, message = "Age should be more than 1")
	@Max(value = 100, message = "Age should be less than 100")
	private byte age;

	@Column(nullable = false, length = 80)
	@NotNull
	@Email(message = "The email is not valid! (need xxxxx@xxxx.xx)")
	@Size(min = 5, max = 80, message = "Email should be between 5 and 80 characters")
	private String email;

	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp createdAt;

	@UpdateTimestamp
	private Timestamp updatedAt;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Role> roles = new HashSet<>();

	public User() {

	}

	public User(String username, String password, String name, byte age, String email) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.age = age;
		this.email = email;
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
	public byte getAge() {
		return age;
	}
	public void setAge(byte age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public void addRole(Role role) {
		roles.add(role);
	}
	public boolean hasRole(Role role) {
		return roles.contains(role);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {

		int result = 37;
		result = 37 * result + id;
		result = 37 * result + ((name == null) ? 0 : name.hashCode());
		result = 37 * result + ((email == null) ? 0 : email.hashCode());
		result = 37 * result + username.hashCode();
		result = 37 * result + (int) age;
		result = 37 * result + createdAt.hashCode();
		result = 37 * result + updatedAt.hashCode();

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
		User other = (User) obj;

		return (id == other.id) &&
				name.equals(other.name) &&
				username.equals(other.username) &&
				(age == other.age) &&
				createdAt.equals(other.createdAt) &&
				updatedAt.equals(other.updatedAt) &&
				email.equals(other.email);
	}
	@Override
	public String toString() {

		return "User [" +
				"id: " + id +
				", username: " + username +
				", password: " + password +
				", name: " + name +
				", age: " 	+ age +
				", email: " + email +
				", roles: " + roles +
				", created at: " + createdAt +
				", updated at: " + updatedAt +
				']';
	}
}
