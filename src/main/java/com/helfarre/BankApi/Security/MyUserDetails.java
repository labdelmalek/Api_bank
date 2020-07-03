package com.helfarre.BankApi.Security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.helfarre.BankApi.Entities.Client;
import com.helfarre.BankApi.Entities.Role;
import com.helfarre.BankApi.Entities.admin;
import com.helfarre.BankApi.Entities.banquier;

public class MyUserDetails implements UserDetails {
	
	// They simply store user information which is later encapsulated into Authentication objects. This allows non-security 
	//related user information (such as email addresses, telephone numbers etc) to be stored in a convenient location.
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private Boolean active;
	private List<GrantedAuthority> authorities;
	public MyUserDetails() {
		
	}

	public MyUserDetails(Object user) {
		String roles="";

		 if (user instanceof Client) {
			 Client userCasted = (Client) user;
			 this.username=userCasted.getEmail();
			this.password=userCasted.getPassword();
			 roles=roles+"ROLE_"+userCasted.getRole()+",";
		}
		 else if (user instanceof banquier) {
			 banquier userCasted = (banquier) user;
			 this.username=userCasted.getEmail();
				this.password=userCasted.getPassword();
				 roles=roles+"ROLE_"+userCasted.getRole()+",";
		}
		 else if (user instanceof admin) {
			 admin userCasted = (admin) user;
			 this.username=userCasted.getEmail();
				this.password=userCasted.getPassword();
				 roles=roles+"ROLE_"+userCasted.getRole()+",";
		}
		
		this.active=true;
		 this.authorities=Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
				 		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.authorities;
		
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
