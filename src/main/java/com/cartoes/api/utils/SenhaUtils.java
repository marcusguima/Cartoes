package com.cartoes.api.utils;
 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
public class SenhaUtils {
 
   	public static String gerarHash(String senha) {
 
          	if (senha == null || senha == "") {
                 	return senha;
          	}
 
          	return new BCryptPasswordEncoder().encode(senha);
 
   	}
 
   	public static boolean compararHash(String senha, String senhaEncoded){
 
          	return new BCryptPasswordEncoder().matches(senha, senhaEncoded);
 
   	}
 
}
