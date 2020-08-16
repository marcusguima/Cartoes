package com.cartoes.api.repositories;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.cartoes.api.entities.Cliente;
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
 @Transactional(readOnly = true)
 Cliente findByCpf(String cpf);

 @Transactional(readOnly = true)
 List<Cliente> findByUfAndDataAtualizacao(String uf, Date dataAtualizacao);
}