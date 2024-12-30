package com.alfaris.ipsh.authservice.service;

import java.util.ArrayList;
import java.util.List;

/*import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;*/
import org.springframework.stereotype.Service;

import com.alfaris.ipsh.authservice.entity.Users;
import com.alfaris.ipsh.authservice.repository.UserRepository;

@Service
public class CustomUserDetailsService /* implements UserDetailsService */{
	/*
	 * 
	 * private final UserRepository userRepository;
	 * 
	 * public CustomUserDetailsService(UserRepository userRepository) {
	 * this.userRepository = userRepository; }
	 * 
	 * @Override public UserDetails loadUserByUsername(String username) throws
	 * UsernameNotFoundException { Users user =
	 * userRepository.findByUserId(username) .orElseThrow(() -> new
	 * UsernameNotFoundException("Username " + username + " not found"));
	 * List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	 * GrantedAuthority e = new SimpleGrantedAuthority("USER"); authorities.add(e);
	 * GrantedAuthority e1 = new SimpleGrantedAuthority("ADMIN");
	 * authorities.add(e1); UserDetails userDetails = new User(user.getUserId(),
	 * user.getPassword(), authorities); return userDetails; }
	 * 
	 */}
