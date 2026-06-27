package com.paramita.bookStore.user.service;


import com.paramita.bookStore.user.TO.LoginResponseTO;
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
	//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaGluZ1JAZ21haWwuY29tIiwiaWF0IjoxNzgyNTUzMDA1LCJleHAiOjE3ODI1NTM4OTV9.3AHJu2c3XGX4CULqHnDVcZTHFPBrmY8KYd7mbR09m_I
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	
	public LoginResponseTO login(LoginReqTo loginReqTo) {
		LoginResponseTO loginResponseTO = new LoginResponseTO();
		Authentication authenticate;
		try {
			authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginReqTo.getUsername(), 
						loginReqTo.getPassword())
				);
		}catch(AuthenticationException exp){
			exp.printStackTrace();
			return null;
		}
		UserDetails userDetails = (UserDetails)authenticate.getPrincipal();
		loginResponseTO.setToken(jwtService.generateToken(userDetails));
		loginResponseTO.setUsername(userDetails.getUsername());
		return loginResponseTO;
		
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
