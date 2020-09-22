package com.cartoes.api.services;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cartoes.api.entities.Cartao;
import com.cartoes.api.entities.Transacao;
import com.cartoes.api.repositories.CartaoRepository;
import com.cartoes.api.repositories.TransacaoRepository;
import com.cartoes.api.utils.ConsistenciaException;



@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransacaoServiceTest {



		@MockBean
		private TransacaoRepository TransacaoRepository;
		
		@MockBean
		private CartaoRepository CartaoRepository;
		
		@Autowired
		private TransacaoService TransacaoService;
		
		private Cartao cartao;
		private Transacao transacao;
		
		@Before
		public void CriarDadosTestes() throws ParseException {

			cartao = new Cartao();
			cartao.setNumero("1111444433337777");
			cartao.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2022"));
			
			transacao = new Transacao();
			transacao.setCartao(cartao);

		}
		
		
		@Test
		public void buscarPorNumeroCartaoEncontrado() throws ConsistenciaException {	
			
			List<Transacao> transacoes = new ArrayList<Transacao>();
			transacoes.add(transacao);
			
			//given
			BDDMockito.given(TransacaoRepository.findByNumeroCartao(Mockito.anyString()))
				.willReturn(Optional.of(transacoes));
			
			//when
			Optional<List<Transacao>> resultado = TransacaoService.buscarPorNumeroCartao("1234");
			
			//then
			assertTrue(resultado.isPresent());
			
		}
		
		@Test(expected = ConsistenciaException.class)
		public void buscarPorNumeroCartaoNaoEncontrado() throws ConsistenciaException {	
			
			//given
			BDDMockito.given(TransacaoRepository.findByNumeroCartao(Mockito.anyString()))
				.willReturn(Optional.empty());
			
			//when
			TransacaoService.buscarPorNumeroCartao("1234");
			
		}
		

		@Test
		public void salvarComSucesso() throws ConsistenciaException {
			
			BDDMockito.given(CartaoRepository.findByNumero(Mockito.anyString())).
				willReturn(Optional.of(cartao));
			BDDMockito.given(TransacaoRepository.save(Mockito.any(Transacao.class))).
				willReturn(new Transacao());

			Transacao resultado = TransacaoService.salvar(transacao);

			assertNotNull(resultado);
			
		}
		
		@Test(expected = ConsistenciaException.class)
		public void salvarCartaoNaoEncontrado() throws ConsistenciaException {
			
			//given
			BDDMockito.given(CartaoRepository.findByNumero(Mockito.anyString())).
			willReturn(Optional.empty());
			
			//when
			TransacaoService.salvar(transacao);
			
		}
		
		@Test(expected = ConsistenciaException.class)
		public void salvarIdTransacaoExistente() throws ConsistenciaException {
			transacao.setId(1);
			//given
			BDDMockito.given(CartaoRepository.findByNumero(Mockito.anyString())).
			willReturn(Optional.of(cartao));
			
			//when
			TransacaoService.salvar(transacao);
			
		}
		
		@Test(expected = ConsistenciaException.class)
		public void salvarCartaoBloqueado() throws ConsistenciaException {
			cartao.setBloqueado(true);
			
			//given
			BDDMockito.given(CartaoRepository.findByNumero(Mockito.anyString())).
			willReturn(Optional.of(cartao));
			
			//when
			TransacaoService.salvar(transacao);
			
		}
		
		@Test(expected = ConsistenciaException.class)
		public void salvarCartaoVencido() throws ConsistenciaException, ParseException {
			cartao.setDataValidade(new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2019"));
			
			//given
			BDDMockito.given(CartaoRepository.findByNumero(Mockito.anyString())).
			willReturn(Optional.of(cartao));
			
			//when
			TransacaoService.salvar(transacao);
			
		}
		

}


