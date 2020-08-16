package com.cartoes.api.controllers;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.cartoes.api.entities.Cartao;
import com.cartoes.api.services.CartaoService;
import com.cartoes.api.utils.ConsistenciaException;
 
@RestController
@RequestMapping("/api/cartao")
@CrossOrigin(origins = "*")
public class CartaoController {
 
   	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);
 
   	@Autowired
   	private CartaoService cartaoService;
 
   	/**
   	 * Retorna os cartões do informado no parâmetro
   	 *
   	 * @param Id do cliente a ser consultado
   	 * @return Lista de cartões que o cliente possui
   	 */
   	@GetMapping(value = "/cliente/{clienteId}")
   	public ResponseEntity<List<Cartao>> buscarPorClienteId(@PathVariable("clienteId") int clienteId) {
 
         	try {
 
                	log.info("Controller: buscando cartões do cliente de ID: {}", clienteId);
 
                	Optional<List<Cartao>> listaCartoes = cartaoService.buscarPorClienteId(clienteId);
 
                	return ResponseEntity.ok(listaCartoes.get());
 
         	} catch (ConsistenciaException e) {
                	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
                	return ResponseEntity.badRequest().body(new ArrayList<Cartao>());
         	} catch (Exception e) {
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(new ArrayList<Cartao>());
         	}
 
   	}
 
   	/**
   	 * Persiste um cliente na base.
   	 *
   	 * @param Dados de entrada do cartao
   	 * @return Dados do cartao persistido
   	 */
   	@PostMapping
   	public ResponseEntity<Cartao> salvar(Cartao cartao) {
 
         	try {
 
                	log.info("Controller: salvando o cartao: {}", cartao.toString());
         	
                	return ResponseEntity.ok(this.cartaoService.salvar(cartao));
 
         	} catch (ConsistenciaException e) {
                	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
                	return ResponseEntity.badRequest().body(new Cartao());
         	} catch (Exception e) {
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(new Cartao());
         	}
 
   	}
   	
   	/**
   	 * Exclui um cartão a partir do id informado no parâmtero
   	 * @param id do cartão a ser excluído
   	 * @return Sucesso/erro
   	 */
   	@DeleteMapping(value = "excluir/{id}")
   	public ResponseEntity<String> excluirPorId(@PathVariable("id") int id){
         	
         	try {
 
                	log.info("Controller: excluíndo cartão de ID: {}", id);
 
                	cartaoService.excluirPorId(id);
 
                	return ResponseEntity.ok("Cartao de id: " + id + " excluído com sucesso");
 
         	} catch (ConsistenciaException e) {
                	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
                	return ResponseEntity.badRequest().body(e.getMensagem());
         	} catch (Exception e) {
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(e.getMessage());
         	}
         	
   	}
 
}
