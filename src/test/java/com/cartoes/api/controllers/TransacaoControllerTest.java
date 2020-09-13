package com.cartoes.api.controllers;
 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import java.util.Optional;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cartoes.api.dtos.TransacaoDto;
import com.cartoes.api.entities.Transacao;
import com.cartoes.api.services.TransacaoService;
import com.cartoes.api.utils.ConsistenciaException;
import com.cartoes.api.utils.ConversaoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") 
public class TransacaoControllerTest {

	@Autowired
   	private MockMvc mvc;
 
   	@MockBean
   	private TransacaoService transacaoService;
	
	private Transacao CriarTransacaoTestes() {
		 
     	Transacao transacao = new Transacao();

     	transacao.setId(1);
     	transacao.setCnpj("15203684000104");
     	transacao.setValor(500.00);
     	transacao.setQdtParcelas(5);
     	transacao.setJuros(0.2);
     	
     	return transacao;
		}
	
	
     	@Test
       	@WithMockUser
       	public void testBuscarPorNumeroCartaoSucesso() throws Exception {
     
             	Transacao transacao = CriarTransacaoTestes();
     
             	BDDMockito.given(transacaoService.buscarPorNumeroCartao(Mockito.anyString()))
                    	.willReturn(Optional.of(transacao));
     
             	mvc.perform(MockMvcRequestBuilders.get("/api/transacao/cartao.numero/5461109310353115")
                    	.accept(MediaType.APPLICATION_JSON))
                    	.andExpect(status().isOk())
                    	.andExpect(jsonPath("$.dados.id").value(transacao.getId()))
                    	.andExpect(jsonPath("$.dados.cnpj").value(transacao.getCnpj()))
                    	.andExpect(jsonPath("$.dados.valor").value(transacao.getValor()))
                    	.andExpect(jsonPath("$.dados.qdtParcelas").value(transacao.getQdtParcelas()))
                    	.andExpect(jsonPath("$.dados.juros").value(transacao.getJuros()))
                    	.andExpect(jsonPath("$.erros").isEmpty());
     
       	}
     	
     	@Test
       	@WithMockUser
       	public void testBuscarPorNumeroCartaoInconsistencia() throws Exception {
     
             	BDDMockito.given(transacaoService.buscarPorNumeroCartao(Mockito.anyString()))
                    	.willThrow(new ConsistenciaException("Teste inconsistência"));
     
             	mvc.perform(MockMvcRequestBuilders.get("/api/transacao/cartao.numero/5461109310353115")
                    	.accept(MediaType.APPLICATION_JSON))
                    	.andExpect(status().isBadRequest())
                    	.andExpect(jsonPath("$.erros").value("Teste inconsistência"));
     
       	}
     	
     	
     	@Test
       	@WithMockUser
       	public void testSalvarSucesso() throws Exception {
     
             	Transacao transacao = CriarTransacaoTestes();
             	TransacaoDto objEntrada = ConversaoUtils.Converter(transacao);
     
             	String json = new ObjectMapper().writeValueAsString(objEntrada);
             	
             	BDDMockito.given(transacaoService.salvar(Mockito.any(Transacao.class)))
                    	.willReturn(transacao);
             	
             	mvc.perform(MockMvcRequestBuilders.post("/api/transacao")
                    	.content(json)
                    	.contentType(MediaType.APPLICATION_JSON)
                    	.accept(MediaType.APPLICATION_JSON))
                    	.andExpect(status().isOk())
                    	.andExpect(jsonPath("$.dados.id").value(objEntrada.getId()))
                    	.andExpect(jsonPath("$.dados.cnpj").value(objEntrada.getCnpj()))
                    	.andExpect(jsonPath("$.dados.valor").value(objEntrada.getValor()))
                    	.andExpect(jsonPath("$.dados.qdtParcelas").value(objEntrada.getQdtParcelas()))
                    	.andExpect(jsonPath("$.dados.juros").value(objEntrada.getJuros()))
                    	.andExpect(jsonPath("$.erros").isEmpty());
     
       	}
       	
       	@Test
       	@WithMockUser
       	public void testSalvarInconsistencia() throws Exception {
     
             	Transacao transacao = CriarTransacaoTestes();
             	TransacaoDto objEntrada = ConversaoUtils.Converter(transacao);
     
             	String json = new ObjectMapper().writeValueAsString(objEntrada);
             	
             	BDDMockito.given(transacaoService.salvar(Mockito.any(Transacao.class)))
                    	.willThrow(new ConsistenciaException("Teste inconsistência."));
             	
             	mvc.perform(MockMvcRequestBuilders.post("/api/transacao")
                    	.content(json)
                    	.contentType(MediaType.APPLICATION_JSON)
                    	.accept(MediaType.APPLICATION_JSON))
                    	.andExpect(status().isBadRequest())
                    	.andExpect(jsonPath("$.erros").value("Teste inconsistência."));
     
       	}
     	
       	
       	
       	@Test
       	@WithMockUser
       	public void testSalvarCnpjEmBranco() throws Exception {
     
             	TransacaoDto objEntrada = new TransacaoDto();
     
             	objEntrada.setValor("500.00");
             	objEntrada.setQdtParcelas("6");
             	objEntrada.setJuros("0.2");
     
             	String json = new ObjectMapper().writeValueAsString(objEntrada);
     
             	mvc.perform(MockMvcRequestBuilders.post("/api/transacao")
                    	.content(json)
                    	.contentType(MediaType.APPLICATION_JSON)
                    	.accept(MediaType.APPLICATION_JSON))
                    	.andExpect(status().isBadRequest())
                    	.andExpect(jsonPath("$.erros").value("CNPJ não pode ser vazio."));
     
       	}
             	
       	@Test
       	@WithMockUser
       	public void testSalvarCnpjInvalido() throws Exception {
     
             	TransacaoDto objEntrada = new TransacaoDto();
     
             	objEntrada.setCnpj("12312312312");
             	objEntrada.setValor("500.00");
             	objEntrada.setQdtParcelas("6");
             	objEntrada.setJuros("0.2");
     
             	String json = new ObjectMapper().writeValueAsString(objEntrada);
     
             	mvc.perform(MockMvcRequestBuilders.post("/api/transacao")
                    	.content(json)
                    	.contentType(MediaType.APPLICATION_JSON)
                    	.accept(MediaType.APPLICATION_JSON))
                    	.andExpect(status().isBadRequest())
                    	.andExpect(jsonPath("$.erros").value("CNPJ inválido."));
     
       	}
       	
       	@Test
       	@WithMockUser
       	public void testSalvarValorEmBranco() throws Exception {
     
             	TransacaoDto objEntrada = new TransacaoDto();
     
             	objEntrada.setCnpj("05887098082");
             	objEntrada.setQdtParcelas("6");
             	objEntrada.setJuros("0.2");
     
             	String json = new ObjectMapper().writeValueAsString(objEntrada);
     
             	mvc.perform(MockMvcRequestBuilders.post("/api/transacao")
                    	.content(json)
                    	.contentType(MediaType.APPLICATION_JSON)
                    	.accept(MediaType.APPLICATION_JSON))
                    	.andExpect(status().isBadRequest())
                    	.andExpect(jsonPath("$.erros").value("Valor não pode ser vazio."));
     
       	}
     	
     	
       	@Test
       	@WithMockUser
       	public void testSalvarValorExcedente() throws Exception {
     
             	TransacaoDto objEntrada = new TransacaoDto();
     
             	objEntrada.setCnpj("05887098082");
             	objEntrada.setValor("12312312312312313123131321239999.99");           	
             	objEntrada.setQdtParcelas("6");
             	objEntrada.setJuros("0.2");
     
             	String json = new ObjectMapper().writeValueAsString(objEntrada);
     
             	mvc.perform(MockMvcRequestBuilders.post("/api/transacao")
                    	.content(json)
                    	.contentType(MediaType.APPLICATION_JSON)
                    	.accept(MediaType.APPLICATION_JSON))
                    	.andExpect(status().isBadRequest())
                    	.andExpect(jsonPath("$.erros").value("Valor deve conter no máximo 10 caracteres."));
     
       	}
     
       	
       	@Test
       	@WithMockUser
       	public void testSalvarQdtParcelasEmBranco() throws Exception {
     
             	TransacaoDto objEntrada = new TransacaoDto();
     
             	objEntrada.setCnpj("05887098082");
             	objEntrada.setValor("500.00");           	
             	objEntrada.setJuros("0.2");
     
             	String json = new ObjectMapper().writeValueAsString(objEntrada);
     
             	mvc.perform(MockMvcRequestBuilders.post("/api/transacao")
                    	.content(json)
                    	.contentType(MediaType.APPLICATION_JSON)
                    	.accept(MediaType.APPLICATION_JSON))
                    	.andExpect(status().isBadRequest())
                    	.andExpect(jsonPath("$.erros").value("Quantidade de Parcelas não pode ser vazio."));
     
       	}
     	
     	
     	
       	@Test
       	@WithMockUser
       	public void testSalvarQdtParcelasExcedente() throws Exception {
     
             	TransacaoDto objEntrada = new TransacaoDto();
     
             	objEntrada.setCnpj("05887098082");
             	objEntrada.setValor("500.00");           	
             	objEntrada.setQdtParcelas("1200");
             	objEntrada.setJuros("0.2");
     
             	String json = new ObjectMapper().writeValueAsString(objEntrada);
     
             	mvc.perform(MockMvcRequestBuilders.post("/api/transacao")
                    	.content(json)
                    	.contentType(MediaType.APPLICATION_JSON)
                    	.accept(MediaType.APPLICATION_JSON))
                    	.andExpect(status().isBadRequest())
                    	.andExpect(jsonPath("$.erros").value("Quantidade de Parcelas deve conter no máximo 2 caracteres."));
     
       	}
       	
       	@Test
       	@WithMockUser
       	public void testSalvarJurosEmBranco() throws Exception {
     
             	TransacaoDto objEntrada = new TransacaoDto();
     
             	objEntrada.setCnpj("05887098082");
             	objEntrada.setValor("500.00");           	
             	objEntrada.setQdtParcelas("1200");
             
     
             	String json = new ObjectMapper().writeValueAsString(objEntrada);
     
             	mvc.perform(MockMvcRequestBuilders.post("/api/transacao")
                    	.content(json)
                    	.contentType(MediaType.APPLICATION_JSON)
                    	.accept(MediaType.APPLICATION_JSON))
                    	.andExpect(status().isBadRequest())
                    	.andExpect(jsonPath("$.erros").value("Juros não pode ser vazio."));
     
       	}
     	
     	
     	
       	@Test
       	@WithMockUser
       	public void testSalvarJurosExcedente() throws Exception {
     
             	TransacaoDto objEntrada = new TransacaoDto();
     
             	objEntrada.setCnpj("05887098082");
             	objEntrada.setValor("500.00");           	
             	objEntrada.setQdtParcelas("6");
             	objEntrada.setJuros("5169900");
     
     
             	String json = new ObjectMapper().writeValueAsString(objEntrada);
     
             	mvc.perform(MockMvcRequestBuilders.post("/api/transacao")
                    	.content(json)
                    	.contentType(MediaType.APPLICATION_JSON)
                    	.accept(MediaType.APPLICATION_JSON))
                    	.andExpect(status().isBadRequest())
                    	.andExpect(jsonPath("$.erros").value("Juros deve conter no máximo 4 caracteres."));
     
       	}
     	
     	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
