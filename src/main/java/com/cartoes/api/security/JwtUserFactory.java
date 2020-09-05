package com.cartoes.api.security;
 
import com.cartoes.api.entities.Usuario;
 
public class JwtUserFactory {
   	
   	public static JwtUser create(Usuario usuario) {
   	
         	return new JwtUser(usuario);	
   	
   	}
   	
}

