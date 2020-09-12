package com.cartoes.api.services;
 
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
 
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
 
import com.cartoes.api.entities.Cliente;
import com.cartoes.api.repositories.ClienteRepository;
import com.cartoes.api.utils.ConsistenciaException;
 
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {
 
   	@MockBean
   	private ClienteRepository clienteRepository;
   	
   	@Autowired
   	private ClienteService clienteService;
   	
   	@Test
   	public void testBuscarPorIdExistente() throws ConsistenciaException { 
         	
         	BDDMockito.given(clienteRepository.findById(Mockito.anyInt()))
                	.willReturn(Optional.of(new Cliente()));
         	
         	Optional<Cliente> resultado = clienteService.buscarPorId(1);
         	
         	assertTrue(resultado.isPresent());
         	
   	}
   	
   	@Test(expected = ConsistenciaException.class)
   	public void testBuscarPorIdNaoExistente() throws ConsistenciaException {  	
         	
         	BDDMockito.given(clienteRepository.findById(Mockito.anyInt()))
                	.willReturn(Optional.empty());
         	
         	clienteService.buscarPorId(1);
         	
   	}
   	
   	@Test
   	public void testBuscarPorCpfExistente() throws ConsistenciaException {
         	
         	BDDMockito.given(clienteRepository.findByCpf(Mockito.anyString()))
                	.willReturn(new Cliente());
         	
         	Optional<Cliente> resultado = clienteService.buscarPorCpf("99930632077");
         	
         	assertTrue(resultado.isPresent());
         	
   	}
   	
   	@Test(expected = ConsistenciaException.class)
   	public void testBuscarPorCpfNaoExistente() throws ConsistenciaException { 	
         	
         	BDDMockito.given(clienteRepository.findByCpf(Mockito.anyString()))
         	.willReturn(null);
         	
         	clienteService.buscarPorCpf("99930632077");
         	
   	}
   	
   	@Test
   	public void testSalvarComSucesso() throws ConsistenciaException {  	
         	
         	BDDMockito.given(clienteRepository.save(Mockito.any(Cliente.class)))
                	.willReturn(new Cliente());
         	
         	Cliente resultado = clienteService.salvar(new Cliente());
         	
         	assertNotNull(resultado);
         	
   	}
   	
   	@Test(expected = ConsistenciaException.class)
   	public void testSalvarIdNaoEncontrado() throws ConsistenciaException {
         	
         	BDDMockito.given(clienteRepository.findById(Mockito.anyInt()))
         	.willReturn(Optional.empty());
         	
         	Cliente c = new Cliente();
         	c.setId(1); // Informar id para satisfazer a condição de busca
         	
         	clienteService.salvar(c);
 
   	}
   	
   	@Test(expected = ConsistenciaException.class)
   	public void testSalvarCpfDuplicado() throws ConsistenciaException {	
         	
         	BDDMockito.given(clienteRepository.save(Mockito.any(Cliente.class)))
         	.willThrow(new DataIntegrityViolationException(""));
         	
         	clienteService.salvar(new Cliente());
 
   	}
   	
}
