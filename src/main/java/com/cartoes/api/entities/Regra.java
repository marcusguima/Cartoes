package com.cartoes.api.entities;
 
import java.io.Serializable;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "Regra")
public class Regra implements Serializable {
 
   	private static final long serialVersionUID = 1L;
   	
   	@Id
   	@GeneratedValue(strategy = GenerationType.AUTO)
   	private int id;
   	
   	@Column(name = "nome", nullable = false, length = 50, unique = true)
   	private String nome;
   	
   	@Column(name = "descricao", nullable = false, length = 255)
   	private String descricao;
   	
   	@Column(name = "ativo", nullable = false)
   	private boolean ativo;
   	
   	public int getId() {
          	return id;
   	}
   	
   	public void setId(int id) {
          	this.id = id;
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
          	return "Regra[id=" + id + ","
                       	+ "nome=" + nome + ","
                       	+ "descricao=" + descricao + ","
                       	+ "ativo=" + ativo + "]";
   	}
   	
}
