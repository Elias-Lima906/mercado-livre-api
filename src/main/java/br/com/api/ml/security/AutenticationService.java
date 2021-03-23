package br.com.api.ml.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.api.ml.user.User;
import br.com.api.ml.user.UserRepository;

@Service
public class AutenticationService implements UserDetailsService {

	private static final String USUARIO_INVALIDO = "Usuário inválido!";

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByEmail(username);

		if (user.isEmpty()) {
			throw new UsernameNotFoundException(USUARIO_INVALIDO);
		}

		return user.get();
	}

}
