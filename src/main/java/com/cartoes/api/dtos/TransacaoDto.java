package com.cartoes.api.dtos;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;



public class TransacaoDto {

	private String id;
   	private String dataTransacao;
   	private String cnpj;
   	private String valor;
   	private String qdtParcelas;
	private String juros;
	private String cartaoId;
	
	
	public String getId() {
     	return id;
	}
	
	public void setId(String id) {
     	this.id = id;
	}
	
	public String getDataTransacao() {
     	return dataTransacao;
	}
	
	public void setDataTransacao(String dataTransacao) {
     	this.dataTransacao = dataTransacao;
	}
	
	public String getCnpj() {
     	return cnpj;
	}
	
	public void setCnpj(String cnpj) {
     	this.cnpj = cnpj;
	}
	
	public String getValor() {
     	return valor;
	}
	
	public void setValor(String valor) {
     	this.valor = valor;
	}
	
	public String getQdtParcelas() {
     	return qdtParcelas;
	}
	
	public void setQdtParcelas(String qdtParcelas) {
     	this.qdtParcelas = qdtParcelas;
	}
	
	public String getJuros() {
     	return juros;
	}
	
	public void setJuros(String juros) {
     	this.juros = juros;
	}
	
	public String getCartaoId() {
     	return cartaoId;
	}
	
	public void setCartaoId(String cartaoId) {
     	this.cartaoId = cartaoId;
	}
	
	
	@Override
   	public String toString() {
         	return "Transacao[id=" + id + ","
                       	+ "dataTransacao=" + dataTransacao + ","
                       	+ "cnpj=" + cnpj + ","
                       	+ "valor=" + valor + ","
                       	+ "qdtParcelas=" + qdtParcelas + ","
                       	+ "juros=" + juros + ","
                       	+ "cartaoId=" + cartaoId + "]";
   	}
	
	
}
