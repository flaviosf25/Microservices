package com.sboot.cloud.netflix.auth.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sboot.cloud.netflix.auth.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userRepository.findByUserName(username);
		if(user != null) {
			return user;
		}else {
			throw new UsernameNotFoundException(" Username ".concat(username).concat(" not found"));
		}
	}

}
