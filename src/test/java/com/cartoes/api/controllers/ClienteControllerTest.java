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
 
import com.cartoes.api.dtos.ClienteDto;
import com.cartoes.api.entities.Cliente;
import com.cartoes.api.services.ClienteService;
import com.cartoes.api.utils.ConsistenciaException;
import com.cartoes.api.utils.ConversaoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteControllerTest {
 
   	@Autowired
   	private MockMvc mvc;
 
   	@MockBean
   	private ClienteService clienteService;
 
   	private Cliente CriarClienteTestes() {
 
         	Cliente cliente = new Cliente();
 
         	cliente.setId(1);
         	cliente.setNome("Teste inclusão");
         	cliente.setCpf("05887098082");
         	cliente.setUf("PR");
 
         	return cliente;
 
   	}
 
   	@Test
   	@WithMockUser
   	public void testBuscarPorIdSucesso() throws Exception {
 
         	Cliente cliente = CriarClienteTestes();
 
         	BDDMockito.given(clienteService.buscarPorId(Mockito.anyInt()))
                	.willReturn(Optional.of(cliente));
 
         	mvc.perform(MockMvcRequestBuilders.get("/api/cliente/1")
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isOk())
                	.andExpect(jsonPath("$.dados.id").value(cliente.getId()))
                	.andExpect(jsonPath("$.dados.nome").value(cliente.getNome()))
                	.andExpect(jsonPath("$.dados.cpf").value(cliente.getCpf()))
                	.andExpect(jsonPath("$.dados.uf").value(cliente.getUf()))
                	.andExpect(jsonPath("$.erros").isEmpty());
 
   	}
 
   	@Test
   	@WithMockUser
   	public void testBuscarPorIdInconsistencia() throws Exception {
 
         	BDDMockito.given(clienteService.buscarPorId((Mockito.anyInt())))
                	.willThrow(new ConsistenciaException("Teste inconsistência"));
 
         	mvc.perform(MockMvcRequestBuilders.get("/api/cliente/1")
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isBadRequest())
                	.andExpect(jsonPath("$.erros").value("Teste inconsistência"));
 
   	}
   	
   	@Test
   	@WithMockUser
   	public void testBuscarPorCpfSucesso() throws Exception {
 
         	Cliente cliente = CriarClienteTestes();
 
         	BDDMockito.given(clienteService.buscarPorCpf(Mockito.anyString()))
                	.willReturn(Optional.of(cliente));
 
         	mvc.perform(MockMvcRequestBuilders.get("/api/cliente/cpf/05887098082")
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isOk())
                	.andExpect(jsonPath("$.dados.id").value(cliente.getId()))
                	.andExpect(jsonPath("$.dados.nome").value(cliente.getNome()))
                	.andExpect(jsonPath("$.dados.cpf").value(cliente.getCpf()))
                	.andExpect(jsonPath("$.dados.uf").value(cliente.getUf()))
                	.andExpect(jsonPath("$.erros").isEmpty());
 
   	}
 
   	@Test
   	@WithMockUser
   	public void testBuscarPorCpfInconsistencia() throws Exception {
 
         	BDDMockito.given(clienteService.buscarPorCpf(Mockito.anyString()))
                	.willThrow(new ConsistenciaException("Teste inconsistência"));
 
         	mvc.perform(MockMvcRequestBuilders.get("/api/cliente/cpf/05887098082")
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isBadRequest())
                	.andExpect(jsonPath("$.erros").value("Teste inconsistência"));
 
   	}
 
   	@Test
   	@WithMockUser
   	public void testSalvarSucesso() throws Exception {
 
         	Cliente cliente = CriarClienteTestes();
         	ClienteDto objEntrada = ConversaoUtils.Converter(cliente);
 
         	String json = new ObjectMapper().writeValueAsString(objEntrada);
         	
         	BDDMockito.given(clienteService.salvar(Mockito.any(Cliente.class)))
                	.willReturn(cliente);
         	
         	mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
                	.content(json)
                	.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isOk())
                	.andExpect(jsonPath("$.dados.id").value(objEntrada.getId()))
                	.andExpect(jsonPath("$.dados.nome").value(objEntrada.getNome()))
                	.andExpect(jsonPath("$.dados.cpf").value(objEntrada.getCpf()))
                	.andExpect(jsonPath("$.dados.uf").value(objEntrada.getUf()))
                	.andExpect(jsonPath("$.erros").isEmpty());
 
   	}
   	
   	@Test
   	@WithMockUser
   	public void testSalvarInconsistencia() throws Exception {
 
         	Cliente cliente = CriarClienteTestes();
         	ClienteDto objEntrada = ConversaoUtils.Converter(cliente);
 
         	String json = new ObjectMapper().writeValueAsString(objEntrada);
         	
         	BDDMockito.given(clienteService.salvar(Mockito.any(Cliente.class)))
                	.willThrow(new ConsistenciaException("Teste inconsistência."));
         	
         	mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
                	.content(json)
                	.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isBadRequest())
                	.andExpect(jsonPath("$.erros").value("Teste inconsistência."));
 
   	}
   	
   	@Test
   	@WithMockUser
   	public void testSalvarNomeEmBranco() throws Exception {
 
         	ClienteDto objEntrada = new ClienteDto();
 
         	objEntrada.setCpf("05887098082");
         	objEntrada.setUf("PR");
 
         	String json = new ObjectMapper().writeValueAsString(objEntrada);
 
         	mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
                	.content(json)
                	.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isBadRequest())
                	.andExpect(jsonPath("$.erros").value("Nome não pode ser vazio."));
 
   	}
   	
   	@Test
   	@WithMockUser
   	public void testSalvarNomeInsuficiente() throws Exception {
 
         	ClienteDto objEntrada = new ClienteDto();
 
         	objEntrada.setNome("abcd");
         	objEntrada.setCpf("05887098082");
         	objEntrada.setUf("PR");
 
         	String json = new ObjectMapper().writeValueAsString(objEntrada);
 
         	mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
                	.content(json)
                	.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isBadRequest())
                	.andExpect(jsonPath("$.erros").value("Nome deve conter entre 5 e 100 caracteres."));
 
   	}
   	
   	@Test
   	@WithMockUser
   	public void testSalvarNomeExcedente() throws Exception {
 
         	ClienteDto objEntrada = new ClienteDto();
 
         	objEntrada.setNome("abcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgij"
         	          	+ "abcdefjgijabcdefjgijabcdefjgijabcdefjgijabcdefjgij1");
         	objEntrada.setCpf("05887098082");
         	objEntrada.setUf("PR");
 
         	String json = new ObjectMapper().writeValueAsString(objEntrada);
 
         	mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
                	.content(json)
                	.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isBadRequest())
                	.andExpect(jsonPath("$.erros").value("Nome deve conter entre 5 e 100 caracteres."));
 
   	}
 
   	@Test
   	@WithMockUser
   	public void testSalvarCpfEmBranco() throws Exception {
 
         	ClienteDto objEntrada = new ClienteDto();
 
         	objEntrada.setNome("Teste inclusão");
         	objEntrada.setUf("PR");
 
         	String json = new ObjectMapper().writeValueAsString(objEntrada);
 
         	mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
                	.content(json)
                	.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isBadRequest())
                	.andExpect(jsonPath("$.erros").value("CPF não pode ser vazio."));
 
   	}
         	
   	@Test
   	@WithMockUser
   	public void testSalvarCpfInvalido() throws Exception {
 
         	ClienteDto objEntrada = new ClienteDto();
 
         	objEntrada.setNome("Teste inclusão");
         	objEntrada.setCpf("12312312312");
         	objEntrada.setUf("PR");
 
         	String json = new ObjectMapper().writeValueAsString(objEntrada);
 
         	mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
                	.content(json)
                	.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isBadRequest())
                	.andExpect(jsonPath("$.erros").value("CPF inválido."));
 
   	}
   	
   	@Test
   	@WithMockUser
   	public void testSalvarUfEmBranco() throws Exception {
 
         	ClienteDto objEntrada = new ClienteDto();
 
         	objEntrada.setNome("Teste inclusão");
         	objEntrada.setCpf("05887098082");
 
         	String json = new ObjectMapper().writeValueAsString(objEntrada);
 
         	mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
                	.content(json)
                	.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isBadRequest())
                	.andExpect(jsonPath("$.erros").value("UF não pode ser vazio."));
 
   	}
   	
   	@Test
   	@WithMockUser
   	public void testSalvarUfInsuficiente() throws Exception {
 
         	ClienteDto objEntrada = new ClienteDto();
 
         	objEntrada.setNome("Teste inclusão");
         	objEntrada.setCpf("05887098082");
         	objEntrada.setUf("P");
 
         	String json = new ObjectMapper().writeValueAsString(objEntrada);
 
         	mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
                	.content(json)
                	.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isBadRequest())
                	.andExpect(jsonPath("$.erros").value("UF deve conter 2 caracteres."));
 
   	}
   	
   	@Test
   	@WithMockUser
   	public void testSalvarUfExcedente() throws Exception {
 
         	ClienteDto objEntrada = new ClienteDto();
 
         	objEntrada.setNome("Teste inclusão");
         	objEntrada.setCpf("05887098082");
         	objEntrada.setUf("PRR");
 
         	String json = new ObjectMapper().writeValueAsString(objEntrada);
 
         	mvc.perform(MockMvcRequestBuilders.post("/api/cliente")
                	.content(json)
                	.contentType(MediaType.APPLICATION_JSON)
                	.accept(MediaType.APPLICATION_JSON))
                	.andExpect(status().isBadRequest())
                	.andExpect(jsonPath("$.erros").value("UF deve conter 2 caracteres."));
 
   	}
   	
}
