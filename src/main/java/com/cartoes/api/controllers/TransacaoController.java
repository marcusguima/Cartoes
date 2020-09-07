package com.cartoes.api.controllers;

//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartoes.api.dtos.TransacaoDto;
import com.cartoes.api.entities.Transacao;
import com.cartoes.api.response.Response;
import com.cartoes.api.services.TransacaoService;
import com.cartoes.api.utils.ConsistenciaException;
import com.cartoes.api.utils.ConversaoUtils;

@RestController
@RequestMapping("/api/transacao")
@CrossOrigin(origins = "*")
public class TransacaoController {

	@Autowired
   	private TransacaoService transacaoService;
	
	
	private static final Logger log = LoggerFactory.getLogger(CartaoController.class);
	

 	@GetMapping(value = "/cartao/{numeroCartao}")
   	public ResponseEntity<Response<List<TransacaoDto>>> buscarPorNumeroCartao(@PathVariable("numeroCartao") String numeroCartao) {
 
 		
 			Response<List<TransacaoDto>> response = new Response<List<TransacaoDto>>();
 		
         	try {
     	
                	Optional<List<Transacao>> listaTransacao = transacaoService.buscarPorNumeroCartao(numeroCartao);
                	
                	response.setDados(ConversaoUtils.ConverterListaTransacao(listaTransacao.get()));
                	
                	return ResponseEntity.ok(response);
 
         	} catch (ConsistenciaException e) {
                	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
                	response.adicionarErro(e.getMensagem());
                	return ResponseEntity.badRequest().body(response);
         	} catch (Exception e) {
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(response);
         	}
 
   	}
 		
	
 	@PostMapping
   	public ResponseEntity<Response<TransacaoDto>> salvar(@Valid @RequestBody TransacaoDto transacaoDto, 
   			BindingResult result) {
 
 			Response<TransacaoDto> response = new Response<TransacaoDto>();
 		
         	try {
 
                	log.info("Controller: salvando a transacao: {}", transacaoDto.toString());
         	
                	if (result.hasErrors()) {
                		 
                       	for (int i = 0; i < result.getErrorCount(); i++) {
                       	   	response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
                       	}
 
                       	log.info("Controller: Os campos obrigatórios não foram preenchidos");
                       	return ResponseEntity.badRequest().body(response);
 
                	}
                	
                	Transacao transacao = this.transacaoService.salvar(ConversaoUtils.Converter(transacaoDto));

                	response.setDados(ConversaoUtils.Converter(transacao)); 
                	
                	return ResponseEntity.ok(response);
 
         	} catch (ConsistenciaException e) {
                	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
                	response.adicionarErro(e.getMensagem());
                	return ResponseEntity.badRequest().body(response);
         	} catch (Exception e) {
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	response.adicionarErro("Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(response);
         	}
 
   	}
	
	
}
