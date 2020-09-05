package com.cartoes.api.security.services;
 
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 
import com.cartoes.api.entities.Usuario;
import com.cartoes.api.security.JwtUserFactory;
import com.cartoes.api.services.UsuarioService;
import com.cartoes.api.utils.ConsistenciaException;
 
@Service
public class JwtUserDetailsService implements UserDetailsService {
 
   	@Autowired
   	private UsuarioService usuarioService;
 
   	@Override
   	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 
         	Optional<Usuario> usuario;
         	
         	try {
 
                	usuario = usuarioService.verificarCredenciais(username);
 
                	return JwtUserFactory.create(usuario.get());
 
         	} catch (ConsistenciaException e) {
                	throw new UsernameNotFoundException(e.getMensagem());
         	}
         	
   	}
 
}
