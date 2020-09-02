package com.cartoes.api.dtos;
 
import javax.validation.constraints.NotEmpty;
 
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
 
public class ClienteDto {
 
   	private String id;
   	
   	@NotEmpty(message = "Nome não pode ser vazio.")
   	@Length(min = 5, max = 100,
   	message = "Nome deve conter entre 5 e 100 caracteres.")
   	private String nome;
   	
   	@NotEmpty(message = "CPF não pode ser vazio.")
   	@CPF( message = "CPF inválido.")
   	private String cpf;
   	
   	@NotEmpty(message = "UF não pode ser vazio.")
   	@Length(min = 2, max = 2,
   	message = "UF deve conter 2 caracteres.")
   	private String uf;
   	
   	public String getId() {
         	return id;
   	}
   	
   	public void setId(String id) {
         	this.id = id;
   	}
   	
   	public String getNome() {
         	return nome;
   	}
   	
   	public void setNome(String nome) {
         	this.nome = nome;
   	}
   	
   	public String getCpf() {
         	return cpf;
   	}
   	
   	public void setCpf(String cpf) {
         	this.cpf = cpf;
   	}
   	
   	public String getUf() {
         	return uf;
   	}
   	
   	public void setUf(String uf) {
         	this.uf = uf;
   	}
         	
   	@Override
   	public String toString() {
         	return "Cliente[id=" + id + ","
                       	+ "nome=" + nome + ","
                       	+ "cpf=" + cpf + ","
                       	+ "uf=" + uf + "]";
   	}
}
