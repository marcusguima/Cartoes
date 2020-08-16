
package com.cartoes.api.utils;
 
public class ConsistenciaException extends Exception {
 
   	private static final long serialVersionUID = 1L;
 
   	private String mensagem;
   	
   	public ConsistenciaException(String mensagem) {
         	this.mensagem = mensagem;
   	}
   	
   	public ConsistenciaException(String format, Object... args) {
         	
         	this.mensagem = format;
         	
         	for (Object object : args) {
                	
                	if (object != null)
                       	mensagem = mensagem.replaceFirst("\\{\\}", object.toString());
                	
         	}
         	
   	}
 
   	public String getMensagem() {
         	return mensagem;
   	}

   	public void setMensagem(String mensagem) {
         	this.mensagem = mensagem;
   	}
   	
}
