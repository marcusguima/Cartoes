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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {
 private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private int id;

 @Column(name = "nome", nullable = false, length = 100)
 private String nome;

 @Column(name = "cpf", nullable = false, length = 11, unique = true)
 private String cpf;

 @Column(name = "uf", nullable = false, length = 2)
 private String uf;

 @Column(name = "data_Atualizacao", nullable = false)
 private Date dataAtualizacao;

 @JsonManagedReference
 @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 private List<Cartao> cartoes;

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

 public String getUf() {
 return uf;
 }

 public void setUf(String uf) {
 this.uf = uf;
 }

 public Date getDataAtualizacao() {
 return dataAtualizacao;
 }

 public void setDataAtualizacao(Date dataAtualizacao) {
 this.dataAtualizacao = dataAtualizacao;
 }
 public List<Cartao> getCartoes() {
 return cartoes;
 }

 public void setCartoes(List<Cartao> cartoes) {
 this.cartoes = cartoes;
 }
 @PreUpdate
 public void preUpdate() {
 dataAtualizacao = new Date();
 }
 @PrePersist
 public void prePersist() {
 dataAtualizacao = new Date();
 }
 @Override
 public String toString() {
 return "Cliente[" + "id=" + id + ","
 + "nome=" + nome + ","
 + "cpf=" + cpf + ","
 + "uf=" + uf + ","
 + "dataAtualizacao=" + dataAtualizacao + "]";
 }
}
