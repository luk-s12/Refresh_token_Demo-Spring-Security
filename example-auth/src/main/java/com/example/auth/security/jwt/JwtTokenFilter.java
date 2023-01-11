package com.example.auth.security.jwt;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.auth.exceptions.InvalidTokenException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private final static Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String auth = request.getHeader("Authorization");

		if (this.jwtTokenUtil.isToken(auth)) {

			String token = this.jwtTokenUtil.getToken(auth);

			if ( ! this.jwtTokenUtil.isAccessToken(token) ) {
				log.warn("El usuario trato de acceder con un refresh token en el header");
				throw new InvalidTokenException("Debe acceder con un access token valido");
			}
			
			String username = this.jwtTokenUtil.getUsernameFromToken(token);
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);

		}

		filterChain.doFilter(request, response);

	}

}
