package com.car.rentservice.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.car.rentservice.modal.User;
import com.car.rentservice.repositories.UserRepository;

@Service
public class JwtTokenService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user.getEmail().equals(email)) {
			return new org.springframework.security.core.userdetails.User(email, user.getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
	}
}
