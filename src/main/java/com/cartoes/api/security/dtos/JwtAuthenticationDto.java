package com.cartoes.api.security.dtos;
 
import javax.validation.constraints.NotEmpty;
 
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
 
public class JwtAuthenticationDto {
 
   	@NotEmpty(message = "CPF não pode ser vazio.")
   	@CPF( message = "CPF inválido.")
   	private String cpf;
   	
   	@NotEmpty(message = "Senha não pode ser vazia.")
   	@Length(min = 8, max = 25,
   	message = "Senha atual deve conter entre 8 e 25 caracteres.")
   	private String senha;
   	
   	public String getCpf() {
         	return cpf;
   	}
   	
   	public void setCpf(String cpf) {
         	this.cpf = cpf;
   	}
 
   	public String getSenha() {
         	return senha;
   	}
   	
   	public void setSenha(String senha) {
         	this.senha = senha;
   	}
 
   	@Override
   	public String toString() {
         	return "JwtAuthenticationRequestDto[cpf=" + cpf + ", senha=" + senha + "]";
   	}
   	
}
