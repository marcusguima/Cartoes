package com.cartoes.api.entities;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@Table(name = "transacao")




public class Transacao implements Serializable {
	
private static final long serialVersionUID = 1L;
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;

@Column(name = "data_Transacao", nullable = false)
private Date dataTransacao;

@Column(name = "cnpj", nullable = false, length = 14, unique = true)
private String cnpj;

@Column(name = "valor", nullable = false)
private Double valor;

@Column(name = "qdt_Parcelas", nullable = false, length = 11)
private int qdtParcelas;

@Column(name = "juros", nullable = false)
private int juros;
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
