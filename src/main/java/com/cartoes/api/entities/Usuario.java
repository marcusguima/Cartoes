package com.cartoes.api.entities;
 
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
 
@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {
 
   	private static final long serialVersionUID = 1L;
   	
   	@Id
   	@GeneratedValue(strategy = GenerationType.AUTO)
   	private int id;
   	
   	@Column(name = "nome", nullable = false, length = 100)
   	private String nome;
   	
   	@Column(name = "cpf", nullable = false, length = 11, unique = true)
   	private String cpf;
   	
   	@Column(name = "senha", nullable = false, length = 100)
   	private String senha;
   	
   	@Column(name = "ativo", nullable = false)
   	private boolean ativo;
   	
	@Column(name = "data_Acesso", nullable = false)
   	private Date dataAcesso;
   	
   	
   	
   	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   	@JoinTable(name = "Usuario_Regra",
   	           joinColumns = { @JoinColumn(name = "usuario_id") },
   	           inverseJoinColumns = { @JoinColumn(name = "regra_id") })
   	private List<Regra> regras;
 
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
   	
   	public boolean getAtivo() {
          	return ativo;
   	}
   	
   	public void setAtivo(boolean ativo) {
          	this.ativo = ativo;
   	}
   	
   	public Date getDataAcesso() {
   	return dataAcesso;
   	}

   	public void setDataAcesso(Date dataAcesso) {
   	this.dataAcesso = dataAcesso;
   	}
 
   	public List<Regra> getRegras() {
          	return regras;
   	}
   	
   	public void setRegras(List<Regra> regras) {
          	this.regras = regras;
   	}
 
   	
   	@PreUpdate
    public void preUpdate() {
    dataAcesso = new Date();
    }
    @PrePersist
    public void prePersist() {
    dataAcesso = new Date();
    }
   	
   	
   	
   	@Override
   	public String toString() {
          	return "Usuario[id=" + id + ","
                       	+ "nome=" + nome + ","
                       	+ "cpf=" + cpf + ","
                       	+ "senha=" + senha + ","
                       	+ "ativo=" + ativo + ","
                       	+ "dataAcesso=" + dataAcesso + "]";
   	}
 
}
