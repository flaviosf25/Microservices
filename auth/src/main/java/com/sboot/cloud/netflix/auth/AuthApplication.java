package com.sboot.cloud.netflix.auth;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sboot.cloud.netflix.auth.entity.Permission;
import com.sboot.cloud.netflix.auth.entity.User;
import com.sboot.cloud.netflix.auth.repository.PermissionRepository;
import com.sboot.cloud.netflix.auth.repository.UserRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(UserRepository userRepository, PermissionRepository permissionRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		return args -> {
			initUsers(userRepository, permissionRepository, bCryptPasswordEncoder);
		};
	}
	
	private void initUsers(UserRepository userRepository, PermissionRepository permissionRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		
		Permission permission = null;
		Permission findPermission = permissionRepository.findByDescription("Admin");
		
		if(findPermission == null) {
			permission = new Permission();
			permission.setDescription("Admin");
			permission = permissionRepository.save(permission);
		}else {
			permission = findPermission;
		}
		
		User admin = new User();
		admin.setUserName("Flavio");
		admin.setAccountNonExpired(true);
		admin.setAccountNonLocked(true);
		admin.setCredentialsNonExpired(true);
		admin.setEnabled(true);
		admin.setPassword(bCryptPasswordEncoder.encode("123456"));
		admin.setPermissions(Arrays.asList(permission));
		
		User find = userRepository.findByUserName("Flavio");
		if(find == null) {
			userRepository.save(admin);
		}
	}

}
