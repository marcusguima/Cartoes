package com.cartoes.api.services;
 
import java.util.List;
import java.util.Optional;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.cartoes.api.entities.Regra;
import com.cartoes.api.repositories.RegraRepository;
import com.cartoes.api.utils.ConsistenciaException;
 
@Service
public class RegraService {
 
   	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);
 
   	@Autowired
   	private RegraRepository regraRepository;
 
   	public Optional<Regra> buscarPorId(int id) throws ConsistenciaException {
 
          	log.info("Service: buscando uma regra com o id: {}", id);
 
          	Optional<Regra> regra = regraRepository.findById(id);
 
          	if (!regra.isPresent()) {
 
                 	log.info("Service: Nenhuma regra com id: {} foi encontrada", id);
                 	throw new ConsistenciaException("Nenhuma regra com id: {} foi encontrada", id);
 
          	}
 
          	return regra;
 
   	}
 
   	public Optional<Regra> buscarPorNome(String nome) throws ConsistenciaException {
 
          	log.info("Service: buscando uma regra com o nome: '{}'", nome);
 
          	Optional<Regra> regra = Optional.ofNullable(regraRepository.findByNome(nome));
 
          	if (!regra.isPresent()) {
 
                 	log.info("Service: Nenhuma regra com nome: '{}' foi encontrada", nome);
                 	throw new ConsistenciaException("Nenhuma regra com nome: '{}' foi encontrada", nome);
 
          	}
 
          	return regra;
 
   	}
 
   	public Optional<List<Regra>> buscarTodasAsRegras() throws ConsistenciaException {
 
          	log.info("Service: buscando todas as regras");
 
          	Optional<List<Regra>> regras = Optional.ofNullable(regraRepository.findAll());
 
          	if (!regras.isPresent() || regras.get().size() < 1) {
 
                 	log.info("Service: Nenhuma regra foi encontrada");
                 	throw new ConsistenciaException("Nenhuma regra foi encontrada");
 
          	}
 
          	return regras;
 
   	}
}
