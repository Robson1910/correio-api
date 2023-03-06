package io.github.robson.correios_api;

import io.github.robson.correios_api.entities.Correio;
import io.github.robson.correios_api.service.CorreioService;
import io.github.robson.correios_api.service.exception.NoContentException;
import org.assertj.core.internal.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class CorreiosApiApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CorreioService service;

	@Test
	@Order(1)
	public void testLinkEmpty() throws Exception {
		mockMvc.perform(post("/insert"))
				.andExpect(status().is(400));
	}

	@Test
	@Order(2)
	public void testLinkInvalidServiceException() {
		String linkInvalido = "https://github.com/user/repo/main/db.json";
		assertThrows(Exception.class, () -> service.Insert(linkInvalido));
	}

	@Test
	@Order(3)
	public void testCepNoExist() throws Exception {
		mockMvc.perform(get("/cep/12345678"))
				.andExpect(status().is(204));
	}

	@Test()
	@Order(4)
	public void testCepNoContentException()  {
		String CepInvalido = "123456";
		assertThrows(NoContentException.class, () -> service.getCorreioByCep(CepInvalido));
	}

	@Test
	@Order(5)
	public void testSaveLink() throws Exception {
		mockMvc.perform(post("/insert")
						.content("https://beamish-crostata-f9ef8b.netlify.app"))
				.andExpect(status().is(201));
	}

	@Test
	@Order(6)
	public void testGetCep() throws Exception {
		mockMvc.perform(get("/cep/70680579"))
				.andExpect(status().is(200));
	}

	@Test
	@Order(7)
	public void testGetCorreioCep() throws Exception {

		MvcResult result =	mockMvc.perform(get("/cep/29700865"))
				.andExpect(status().isOk()).andReturn();

		String resultString = result.getResponse().getContentAsString();

		String correio = new ObjectMapper().writeValueAsString(
				Correio.builder()
						.estado("ES")
						.cidade("Colatina")
						.bairro("Bela Vista")
						.cep("29700865")
						.endereco("Rua Muniz Freire")
						.build());

		JSONAssert.assertEquals(correio,resultString,true);
	}
}
