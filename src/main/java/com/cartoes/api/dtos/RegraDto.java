package com.cartoes.api.dtos;
 
public class RegraDto {
 
   	private String nome;
   	private String descricao;
   	private boolean ativo;
 
   	public RegraDto() { }
   	
   	public RegraDto(String nome) {
         	this.nome = nome;
   	}
   	
   	public String getNome() {
         	return nome;
   	}
   	public void setNome(String nome) {
         	this.nome = nome;
   	}
   	
   	public String getDescricao() {
         	return descricao;
   	}
   	public void setDescricao(String descricao) {
         	this.descricao = descricao;
   	}
   	
   	public boolean getAtivo() {
         	return ativo;
   	}
   	public void setAtivo(boolean ativo) {
         	this.ativo = ativo;
   	}
   	
   	@Override
   	public String toString() {
         	return "Regra[nome=" + nome + ","
                       	+ "descricao=" + descricao + "]";
   	}
   	
}
