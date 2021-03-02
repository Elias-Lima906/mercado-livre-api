package br.com.api.ml.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.api.ml.user.User;
import br.com.api.ml.user.UserRepository;

public class JwtRequestFilter extends OncePerRequestFilter {

	private TokenService tokenService;

	private UserRepository userRepository;

	public JwtRequestFilter() {

	}

	public JwtRequestFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = this.getToken(request);

		boolean isValid = tokenService.isTokenValid(token);

		if (isValid) {
			autenticateUser(token);
		}

		filterChain.doFilter(request, response);
	}

	private void autenticateUser(String token) {

		Long idUsuario = this.tokenService.getIdUsuario(token);

		User user = userRepository.findById(idUsuario).get();

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
				user.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

	}

	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}

		return token.substring(7, token.length());
	}

}
