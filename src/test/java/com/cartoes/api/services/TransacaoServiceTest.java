package com.cartoes.api.services;
 
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cartoes.api.entities.Cartao;
import com.cartoes.api.entities.Cliente;
import com.cartoes.api.entities.Transacao;
import com.cartoes.api.repositories.ClienteRepository;
import com.cartoes.api.repositories.TransacaoRepository;
import com.cartoes.api.utils.ConsistenciaException;
 
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransacaoServiceTest {

	@MockBean
   	private TransacaoRepository transacaoRepository;
   	
   	@Autowired
   	private TransacaoService transacaoService;
	
	@Test
   	public void testBuscarPorNumeroCartaoExistente() throws ConsistenciaException {
         	
		
		Optional<List<Transacao>> resultado = transacaoService.buscarPorNumeroCartao("5461109310353115");		
         	
		BDDMockito.given(transacaoRepository.findByNumeroCartao(Mockito.anyString()))
                	.willReturn(resultado);
         	
         	//Optional<List<Transacao>> resultado = transacaoService.buscarPorNumeroCartao("5461109310353115");
         	
        	assertTrue(resultado.isPresent());       	
   	}
   	
   	@Test(expected = ConsistenciaException.class)
   	public void testBuscarPorNumeroCartaoNaoExistente() throws ConsistenciaException { 	
         	
         	BDDMockito.given(transacaoRepository.findByNumeroCartao(Mockito.anyString()))
         	.willReturn(null);
         	
         	transacaoService.buscarPorNumeroCartao("5461109310353115");
         	
   	}
	
	
	
}
