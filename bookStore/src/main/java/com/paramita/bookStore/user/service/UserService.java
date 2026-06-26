package com.paramita.bookStore.user.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paramita.bookStore.security.config.JWTService;
import com.paramita.bookStore.user.TO.LoginReqTo;
import com.paramita.bookStore.user.TO.UserTo;
import com.paramita.bookStore.user.entity.User;
import com.paramita.bookStore.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	
	public String login(LoginReqTo loginReqTo) {
		Authentication authenticate;
		try {
			authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginReqTo.getUsername(), 
						loginReqTo.getPassword())
				);
		}catch(AuthenticationException exp){
			exp.printStackTrace();
			return "User is not Authenticate";
		}
		UserDetails userDetails = (UserDetails)authenticate.getPrincipal();
		return jwtService.generateToken(userDetails);
		
	}
	
	public UserTo register(UserTo user) {
		/*
		 * User result = new User().builder() .email(user.getEmail())
		 * .firstName(user.getFirstName()) .lastName(user.getLastName())
		 * .password(passwordEncoder.encode(user.getPassword())) .build();
		 */
		User result=this.userRepository.save(new User().builder()
				.email(user.getEmail())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.password(passwordEncoder.encode(user.getPassword()))
				.build());
		return new UserTo().builder()
				.id(result.getId())
				.email(result.getEmail())
				.firstName(result.getFirstName())
				.lastName(result.getLastName())
				.password(result.getPassword())
				.build();
	}

	public List<UserTo> getUserList(){
		UserTo user = new UserTo();
		List<User> result = userRepository.findAll();
		return result.stream().map(e->{
			user.setId(e.getId());
			user.setEmail(e.getEmail());
			user.setFirstName(e.getFirstName());
			user.setLastName(e.getLastName());
			return user;
		}).toList();
	}
}
