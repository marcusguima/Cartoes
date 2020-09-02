package com.cartoes.api.response;
 
import java.util.ArrayList;
import java.util.List;
 
public class Response<T> {
 
   	private T dados;
   	private List<String> erros;
   	
   	public Response() {
         	erros = new ArrayList<String>();
   	}
   	
   	public Response(T dados) {
         	erros = new ArrayList<String>();
         	this.dados = dados;
   	}
   	
   	public T getDados() {
         	return dados;
   	}
   	
   	public void setDados(T dados) {
         	this.dados = dados;
   	}
   	
   	public List<String> getErros() {
         	return erros;
   	}
   	
   	public void setErros(List<String> erros) {
         	this.erros = erros;
   	}
   	
   	public void adicionarErro(String txt) {
         	this.erros.add(txt);
   	}
   	
   	public void adicionarErro(String format, Object... args) {
         	
         	String aux = format;
         	
         	for (Object object : args) {
                	aux = aux.replaceFirst("\\{\\}", object.toString());
         	}
         	
         	this.erros.add(aux);
         	
   	}
 
}
