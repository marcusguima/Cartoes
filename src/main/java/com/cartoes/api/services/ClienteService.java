package com.cartoes.api.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.cartoes.api.entities.Cliente;
import com.cartoes.api.repositories.ClienteRepository;
import com.cartoes.api.utils.ConsistenciaException;
@Service
public class ClienteService {
 private static final Logger log = LoggerFactory.getLogger(ClienteService.class);
 
 @Autowired
 private ClienteRepository clienteRepository;
 
 public Optional<Cliente> buscarPorId(int id) throws ConsistenciaException {

 log.info("Service: buscando um clinte com o id: {}", id);

 Optional<Cliente> cliente = clienteRepository.findById(id);

 if (!cliente.isPresent()) {
 log.info("Service: Nenhum cliente com id: {} foi encontrado", id);
 throw new ConsistenciaException("Nenhum cliente com id: {} foi encontrado", id);

 }

 return cliente;

 }

 public Optional<Cliente> buscarPorCpf(String cpf) throws ConsistenciaException {

 log.info("Service: buscando um clinte com o cpf: {}", cpf);

 Optional<Cliente> cliente =
Optional.ofNullable(clienteRepository.findByCpf(cpf));

 if (!cliente.isPresent()) {
 log.info("Service: Nenhum cliente com cpf: {} foi encontrado", cpf);
 throw new ConsistenciaException("Nenhum cliente com cpf: {} foi encontrado", cpf);
 }

 return cliente;

 }
 public Cliente salvar(Cliente cliente) throws ConsistenciaException {

 log.info("Service: salvando o cliente: {}", cliente);

 if (cliente.getId() > 0)
 buscarPorId(cliente.getId());

 try {
 return clienteRepository.save(cliente);
 } catch (DataIntegrityViolationException e) {

 log.info("Service: O cpf: {} já está cadastrado para outro cliente",
cliente.getCpf());
 throw new ConsistenciaException("O cpf: {} já está cadastrado paraoutro cliente", cliente.getCpf());

 }
 }
}