package com.iscdemo.controller;

import com.iscdemo.models.basemodel.InvocationContext;
import com.iscdemo.models.basemodel.MainSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.iscdemo.service.JwtUserDetailsService;
import com.iscdemo.security.JwtTokenUtil;
import com.iscdemo.models.model.JwtRequest;

@RestController
@RequestMapping({"/security"})
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<InvocationContext> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		return ResponseEntity.ok(userDetailsService.generateAccessToken(authenticationRequest));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<InvocationContext> saveUser(@RequestBody MainSecurityContext user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}
}