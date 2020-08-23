package com.cartoes.api.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;


import com.cartoes.api.entities.Transacao;
import com.cartoes.api.repositories.TransacaoRepository;
import com.cartoes.api.utils.ConsistenciaException;

public class TransacaoService {

	
	private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);
	
	@Autowired
   	private TransacaoRepository transacaoRepository;
		

	
	
	public Optional<Transacao> buscarPorId(int id) throws ConsistenciaException {
		 
     	log.info("Service: buscando transacao de id: {}", id);

     	Optional<Transacao> transacao = transacaoRepository.findById(id);

     	if (!transacao.isPresent()) {

            	log.info("Service: Nenhuma transacao com id: {} foi encontrada", id);
            	throw new ConsistenciaException("Nenhuma transacao com id: {} foi encontrada", id);

     	}

     	return transacao;

	}
	
	
	
	
 	public Optional<List<Transacao>> buscarPorCartaoNumero(int cartaoNumero) throws ConsistenciaException {
 		 
     	log.info("Service: buscando as transacoes do cartao de numero: {}", cartaoNumero);

     	Optional<List<Transacao>> transacoes = Optional.ofNullable(transacaoRepository.findByCartaoNumero(cartaoNumero));

     	if (!transacoes.isPresent() || transacoes.get().size() < 1) {

            	log.info("Service: Nenhuma transacao encontrada para o cartao de numero: {}", cartaoNumero);
            	throw new ConsistenciaException("Nenhuma transação encontrada para o cartao de numero: {}", cartaoNumero);

     	}

     	return transacoes;

	}
	
	
 	public Transacao salvar(Transacao transacao) throws ConsistenciaException {
 		 
     	log.info("Service: salvando a transacao: {}", transacao);

     	if (!transacaoRepository.findById(transacao.getCartao().getId()).isPresent()) {

            	log.info("Service: Nenhum cartao de numero: {} foi encontrado", transacao.getCartao().getNumero());
            	throw new ConsistenciaException("Nenhum cartao de número: {} foi encontrado", transacao.getCartao().getNumero());

     	}
     	
     	

     	if (transacao.getId() > 0)
            	buscarPorId(transacao.getId());

     	try {

            	return transacaoRepository.save(transacao);

     	} catch (DataIntegrityViolationException e) {

            	log.info("Service: Já existe uma transacao de id: {} cadastrada", transacao.getId());
            	throw new ConsistenciaException("Já existe uma transacao de id: {} cadastrada", transacao.getId());
            	
     	}
     	
     	
 

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
