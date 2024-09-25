package com.wish.dms_api.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wish.dms_api.validator.EmailExist;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User extends Auditable<String> implements UserDetails{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	
	@Column
	private String name;
	
	@Email
	@NotEmpty(message="Email must be given")
	@Column(unique=true)
	private String email;
	
	@NotEmpty(message="An username must be given")
	@Column(unique=true)
	private String username;
	
	@NotEmpty(message="Give a password")
	@Column
	private String password;
//	
//	@Column
//	private boolean accountVerified;
//	
//	@Column
//	private boolean loginDisabled;
	
	@Transient
	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Document> documents;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@OneToMany(mappedBy="user")
//	Set<SecureToken> tokens;
	
	
}
