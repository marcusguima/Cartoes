package com.cartoes.api.controllers;
 
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
 
import com.cartoes.api.dtos.ClienteDto;
import com.cartoes.api.entities.Cliente;
import com.cartoes.api.response.Response;
import com.cartoes.api.services.ClienteService;
import com.cartoes.api.utils.ConsistenciaException;
import com.cartoes.api.utils.ConversaoUtils;
 
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
   	public ResponseEntity<Response<ClienteDto>> buscarPorId(@PathVariable("id") int id){
 
         	Response<ClienteDto> response = new Response<ClienteDto>();
 
         	try {
 
                	log.info("Controller: buscando cliente com id: {}", id);
                	
                	Optional<Cliente> cliente = clienteService.buscarPorId(id);
 
                	response.setDados(ConversaoUtils.Converter(cliente.get()));
 
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
 
   	/**
   	 * Retorna os dados de um cliente a partir do CPF informado
   	 *
   	 * @param Cpf do cliente
   	 * @return Dados do cliente
   	 */
   	@GetMapping(value = "/cpf/{cpf}")
   	public ResponseEntity<Response<ClienteDto>> buscarPorCpf(@PathVariable("cpf") String cpf) {
 
         	Response<ClienteDto> response = new Response<ClienteDto>();
 
         	try {
 
                	log.info("Controller: buscando cliente por CPF: {}", cpf);
 
                	Optional<Cliente> cliente = clienteService.buscarPorCpf(cpf);
 
                	response.setDados(ConversaoUtils.Converter(cliente.get()));
 
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
 
   	/**
   	 * Persiste um cliente na base.
   	 *
   	 * @param Dados de entrada do cliente
   	 * @return Dados do cliente persistido
   	 */
   	@PostMapping
   	public ResponseEntity<Response<ClienteDto>> salvar(@Valid @RequestBody ClienteDto clienteDto, BindingResult result) {
 
         	Response<ClienteDto> response = new Response<ClienteDto>();
 
         	try {
 
                	log.info("Controller: salvando o cliente: {}", clienteDto.toString());
 
                	if (result.hasErrors()) {
 
                       	for (int i = 0; i < result.getErrorCount(); i++) {
                       	   	response.adicionarErro(result.getAllErrors().get(i).getDefaultMessage());
                       	}
                       	
                       	log.info("Controller: Os campos obrigatórios não foram preenchidos");
                       	return ResponseEntity.badRequest().body(response);
 
                	}
 
                	Cliente cliente = this.clienteService.salvar(ConversaoUtils.Converter(clienteDto));
                	response.setDados(ConversaoUtils.Converter(cliente));
 
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
