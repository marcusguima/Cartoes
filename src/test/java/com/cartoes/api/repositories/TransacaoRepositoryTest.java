package com.cartoes.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cartoes.api.entities.Cartao;
import com.cartoes.api.entities.Cliente;
import com.cartoes.api.entities.Transacao;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransacaoRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private TransacaoRepository transacaoRepository;

	private Cliente clienteTeste;
	private Cartao cartaoTeste;
	private Transacao transacaoTeste;

	private void CriarDadosTestes() throws ParseException {

		clienteTeste = new Cliente();

		clienteTeste.setNome("Nome Teste");
		clienteTeste.setCpf("05887098082");
		clienteTeste.setUf("CE");
		clienteTeste.setDataAtualizacao(new Date());

		cartaoTeste = new Cartao();

		cartaoTeste.setNumero("1111444433337777");
		cartaoTeste.setDataValidade(new Date());
		cartaoTeste.setBloqueado(false);
		cartaoTeste.setDataAtualizacao(new Date());
		cartaoTeste.setCliente(clienteTeste);

		transacaoTeste = new Transacao();

		transacaoTeste.setDataTransacao(new Date());
		transacaoTeste.setCnpj("50947875000173");
		transacaoTeste.setValor(100.50);
		transacaoTeste.setQdtParcelas(2);
		transacaoTeste.setJuros(0.06);
		transacaoTeste.setCartao(cartaoTeste);

	}

	@Before
	public void setUp() throws Exception {

		CriarDadosTestes();

		clienteRepository.save(clienteTeste);
		cartaoRepository.save(cartaoTeste);
		transacaoRepository.save(transacaoTeste);

	}

	@After
	public void tearDown() throws Exception {

		transacaoRepository.deleteAll();
		cartaoRepository.deleteAll();
		clienteRepository.deleteAll();
		
	}

	@Test
	public void testFindById() {

		Transacao transacao = transacaoRepository.findById(transacaoTeste.getId()).get();
		assertEquals(transacao.getId(), transacaoTeste.getId());

	}

	@Test
	public void testFindByNumeroCartao() {

		List<Transacao> lst = transacaoRepository.findByNumeroCartao(cartaoTeste.getNumero()).get();

		if (lst.size() != 1) {
			fail();
		}

		Transacao transacao = lst.get(0);

		assertTrue(transacao.getId() == transacaoTeste.getId()
				&& transacao.getCnpj().equals(transacaoTeste.getCnpj())
				&& transacao.getValor() == transacaoTeste.getValor()
				&& transacao.getQdtParcelas() == transacaoTeste.getQdtParcelas()
				&& transacao.getJuros() == transacaoTeste.getJuros());

	}

}
