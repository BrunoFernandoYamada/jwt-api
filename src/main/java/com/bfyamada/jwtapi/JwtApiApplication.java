package com.bfyamada.jwtapi;

import com.bfyamada.jwtapi.core.enums.ERole;
import com.bfyamada.jwtapi.core.models.Role;
import com.bfyamada.jwtapi.core.models.User;
import com.bfyamada.jwtapi.core.repositories.RoleRepository;
import com.bfyamada.jwtapi.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@SpringBootApplication
public class JwtApiApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(JwtApiApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		if(roleRepository.findByName(ERole.ROLE_USER).isEmpty()){
			roleRepository.insert(new Role(ERole.ROLE_USER));
		}

		if( roleRepository.findByName(ERole.ROLE_MODERATOR).isEmpty()){
			roleRepository.insert(new Role(ERole.ROLE_MODERATOR));
		}

		if(roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()){
			roleRepository.insert(new Role(ERole.ROLE_ADMIN));
		}

		if(userRepository.findByUsername("admin").isEmpty()){

			User user = new User("admin","admin@admin", encoder.encode("admin1234"));

			Role roleUser = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
			Role roleModerate = roleRepository.findByName(ERole.ROLE_MODERATOR).orElse(null);
			Role roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(null);

			Set<Role> roles = new HashSet<>();
			roles.add(roleUser);
			roles.add(roleModerate);
			roles.add(roleAdmin);

			user.setRoles(roles);

			userRepository.insert(user);
		}
	}
}
