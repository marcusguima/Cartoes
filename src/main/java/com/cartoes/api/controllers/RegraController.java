package com.cartoes.api.controllers;
 
import java.util.List;
import java.util.Optional;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.cartoes.api.dtos.RegraDto;
import com.cartoes.api.entities.Regra;
import com.cartoes.api.response.Response;
import com.cartoes.api.services.RegraService;
import com.cartoes.api.utils.ConsistenciaException;
import com.cartoes.api.utils.ConversaoUtils;
 
@RestController
@RequestMapping("/api/regra")
@CrossOrigin(origins = "*")
public class RegraController {
 
   	private static final Logger log = LoggerFactory.getLogger(RegraController.class);
   	
   	@Autowired
   	private RegraService regraService;
   	
   	/**
   	 * Retorna os dados de uma regra a partir do id informado
   	 *
   	 * @param Id da regra
   	 * @return Dados da regra
   	 */
   	@GetMapping(value = "/{id}")
   	public ResponseEntity<Response<RegraDto>> buscarPorId(@PathVariable("id") int id) {
 
         	Response<RegraDto> response = new Response<RegraDto>();
 
         	try {
 
                	log.info("Controller: buscando Regra com id: {}", id);
 
                	Optional<Regra> regra = regraService.buscarPorId(id);
 
                	response.setDados(ConversaoUtils.Converter(regra.get()));
 
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
   	 * Retorna os dados de uma regra a partir do nome informado
   	 *
   	 * @param Nome da regra
   	 * @return Dados da regra
   	 */
   	@GetMapping(value = "/nome/{nome}")
   	public ResponseEntity<Response<RegraDto>> buscarPorNome(@PathVariable("nome") String nome) {
 
         	Response<RegraDto> response = new Response<RegraDto>();
 
         	try {
 
                	log.info("Controller: buscando Regra com nome: {}", nome);
 
                	Optional<Regra> regra = regraService.buscarPorNome(nome);
 
                	response.setDados(ConversaoUtils.Converter(regra.get()));
 
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
   	 * Retorna os dados de todas as regras cadastradas
   	 *
   	 * @return Lista de regras cadastradas
   	 */
   	@GetMapping(value = "/todas")
   	public ResponseEntity<Response<List<RegraDto>>> buscarTodasAsRegras() {
 
         	Response<List<RegraDto>> response = new Response<List<RegraDto>>();
 
         	try {
 
                	log.info("Controller: buscando todas as regras");
 
                	Optional<List<Regra>> regras = regraService.buscarTodasAsRegras();
 
                	response.setDados(ConversaoUtils.Converter(regras.get()));
 
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
