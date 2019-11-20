package com.educandoweb.course.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.educandoweb.course.entities.Post;
import com.educandoweb.course.entities.Role;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.PostRepository;
import com.educandoweb.course.repositories.RoleRepository;
import com.educandoweb.course.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {

		User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", passwordEncode.encode("123456"));
		User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", passwordEncode.encode("123456"));

		userRepository.saveAll(Arrays.asList(u1, u2));

		Role r1 = new Role(null, "ROLE_MEMBER");
		Role r2 = new Role(null, "ROLE_ADMIN");

		roleRepository.saveAll(Arrays.asList(r1, r2));

		u1.getRoles().add(r1);
		u2.getRoles().add(r1);
		u2.getRoles().add(r2);

		userRepository.saveAll(Arrays.asList(u1, u2));

		Post p1 = new Post(null, Instant.parse("2019-06-20T19:53:07Z"), "Bom dia!", "#Boratrabalhar", u1);
		Post p2 = new Post(null, Instant.parse("2019-06-20T19:53:07Z"), "Hoje", "Que o dia seja belo e o hoje seja produtivo", u2);

		postRepository.saveAll(Arrays.asList(p1, p2));
	}
}
