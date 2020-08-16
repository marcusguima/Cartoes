package com.cartoes.api.services;
 
import java.util.List;
import java.util.Optional;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
 
import com.cartoes.api.entities.Cartao;
import com.cartoes.api.repositories.CartaoRepository;
import com.cartoes.api.repositories.ClienteRepository;
import com.cartoes.api.utils.ConsistenciaException;
 
@Service
public class CartaoService {
 
   	private static final Logger log = LoggerFactory.getLogger(CartaoService.class);
 
   	@Autowired
   	private CartaoRepository cartaoRepository;
 
   	@Autowired
   	private ClienteRepository clienteRepository;
 
   	public Optional<Cartao> buscarPorId(int id) throws ConsistenciaException {
 
         	log.info("Service: buscando os cartoes de id: {}", id);
 
         	Optional<Cartao> cartao = cartaoRepository.findById(id);
 
         	if (!cartao.isPresent()) {
 
                	log.info("Service: Nenhuma cartão com id: {} foi encontrado", id);
                	throw new ConsistenciaException("Nenhuma cartão com id: {} foi encontrado", id);
 
         	}
 
         	return cartao;
 
   	}
 
   	public Optional<List<Cartao>> buscarPorClienteId(int clienteId) throws ConsistenciaException {
 
         	log.info("Service: buscando os cartoes do cliente de id: {}", clienteId);
 
         	Optional<List<Cartao>> cartoes = Optional.ofNullable(cartaoRepository.findByClienteId(clienteId));
 
         	if (!cartoes.isPresent() || cartoes.get().size() < 1) {
 
                	log.info("Service: Nenhum cartão encontrado para o cliente de id: {}", clienteId);
                	throw new ConsistenciaException("Nenhum cartão encontrado para o cliente de id: {}", clienteId);
 
         	}
 
         	return cartoes;
 
   	}
 
   	public Cartao salvar(Cartao cartao) throws ConsistenciaException {
 
         	log.info("Service: salvando o cartao: {}", cartao);
 
         	if (!clienteRepository.findById(cartao.getCliente().getId()).isPresent()) {
 
                	log.info("Service: Nenhum cliente com id: {} foi encontrado", cartao.getCliente().getId());
                	throw new ConsistenciaException("Nenhum cliente com id: {} foi encontrado", cartao.getCliente().getId());
 
         	}
 
         	if (cartao.getId() > 0)
                	buscarPorId(cartao.getId());
 
         	try {
 
                	return cartaoRepository.save(cartao);
 
         	} catch (DataIntegrityViolationException e) {
 
                	log.info("Service: Já existe um cartão de número {} cadastrado", cartao.getNumero());
                	throw new ConsistenciaException("Já existe um cartão de número {} cadastrado", cartao.getNumero());
                	
         	}
 
   	}
   	
   	public void excluirPorId(int id) throws ConsistenciaException {
 
         	log.info("Service: excluíndo o cartão de id: {}", id);
 
         	buscarPorId(id);
         	
         	cartaoRepository.deleteById(id);
 
   	}
 
}
