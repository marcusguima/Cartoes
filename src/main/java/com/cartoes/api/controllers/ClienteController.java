package com.cartoes.api.controllers;
 
import java.util.Optional;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.cartoes.api.entities.Cliente;
import com.cartoes.api.services.ClienteService;
import com.cartoes.api.utils.ConsistenciaException;
 
@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {
 
   	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);
 
   	@Autowired
   	private ClienteService clienteService;
 
   	/**
   	 * Retorna os dados de um cliente a partir do seu id
   	 *
   	 * @param Id do cliente
   	 * @return Dados do cliente
   	 */
   	@GetMapping(value = "/{id}")
   	public ResponseEntity<Cliente> buscarPorId(@PathVariable("id") int id) {
 
         	try {
 
                	log.info("Controller: buscando cliente com id: {}", id);
                	
                	Optional<Cliente> cliente = clienteService.buscarPorId(id);
 
                	return ResponseEntity.ok(cliente.get());
 
         	} catch (ConsistenciaException e) {
                	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
                	return ResponseEntity.badRequest().body(new Cliente());
         	} catch (Exception e) {
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(new Cliente());
         	}
 
   	}
 
   	/**
   	 * Retorna os dados de um cliente a partir do CPF informado
   	 *
   	 * @param Cpf do cliente
   	 * @return Dados do cliente
   	 */
   	@GetMapping(value = "/cpf/{cpf}")
   	public ResponseEntity<Cliente> buscarPorCpf(@PathVariable("cpf") String cpf) {
 
         	try {
 
                	log.info("Controller: buscando cliente por CPF: {}", cpf);
 
                	Optional<Cliente> cliente = clienteService.buscarPorCpf(cpf);
 
                	return ResponseEntity.ok(cliente.get());
 
         	} catch (ConsistenciaException e) {
                	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
                	return ResponseEntity.badRequest().body(new Cliente());
         	} catch (Exception e) {
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(new Cliente());
         	}
 
   	}
 
   	/**
   	 * Persiste um cliente na base.
   	 *
   	 * @param Dados de entrada do cliente
   	 * @return Dados do cliente persistido
   	 */
   	@PostMapping
   	public ResponseEntity<Cliente> salvar(Cliente cliente) {
 
         	try {
 
                	log.info("Controller: salvando o cliente: {}", cliente.toString());
 
                	return ResponseEntity.ok(this.clienteService.salvar(cliente));
 
         	} catch (ConsistenciaException e) {
                	log.info("Controller: Inconsistência de dados: {}", e.getMessage());
                	return ResponseEntity.badRequest().body(new Cliente());
         	} catch (Exception e) {
                	log.error("Controller: Ocorreu um erro na aplicação: {}", e.getMessage());
                	return ResponseEntity.status(500).body(new Cliente());
         	}
 
   	}
 
}
