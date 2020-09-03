package com.cartoes.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartoes.api.entities.Transacao;
import com.cartoes.api.services.TransacaoService;
import com.cartoes.api.utils.ConsistenciaException;

@RestController
@RequestMapping("/api/transacao")
@CrossOrigin(origins = "*")
public class TransacaoController {

	@Autowired
   	private TransacaoService transacaoService;
	
	
	private static final Logger log = LoggerFactory.getLogger(CartaoController.class);
	

 	@GetMapping(value = "/cartao/{numeroCartao}")
   	public ResponseEntity<List<Transacao>> buscarPorNumeroCartao(@PathVariable("numeroCartao") String numeroCartao) {
 
         	try {
 
                	
                	
                	Optional<List<Transacao>> listaTransacao = transacaoService.buscarPorNumeroCartao(numeroCartao);
 
                	return ResponseEntity.ok(listaTransacao.get());
 
         	} catch (ConsistenciaException e) {
                	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
                	return ResponseEntity.badRequest().body(new ArrayList<Transacao>());
         	} catch (Exception e) {
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(new ArrayList<Transacao>());
         	}
 
   	}
 		
	
 	@PostMapping
   	public ResponseEntity<Transacao> salvar(@RequestBody Transacao transacao) {
 
         	try {
 
                	log.info("Controller: salvando a transacao: {}", transacao.toString());
         	
                	return ResponseEntity.ok(transacaoService.salvar(transacao));
 
         	} catch (ConsistenciaException e) {
                	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
                	return ResponseEntity.badRequest().body(new Transacao());
         	} catch (Exception e) {
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(new Transacao());
         	}
 
   	}
	
	
	

	
}
