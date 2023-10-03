package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import io.swagger.v3.oas.models.PathItem.HttpMethod;
import med.voll.api.dto.DatosAgendarConsulta;
import med.voll.api.dto.DatosDetalleConsulta;
import med.voll.api.dto.Especialidad;
import med.voll.api.services.AgendaDeConsultaService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

	
	  @Autowired private MockMvc mvc;
	  
	  @Autowired private JacksonTester<DatosAgendarConsulta>
	  agendarConsultaJacksonTester;
	  
	  @Autowired private JacksonTester<DatosDetalleConsulta>
	  detalleConsultaJacksonTester;
	  
	  @MockBean private AgendaDeConsultaService agendaDeConsultaService;
	 


	
	@Test
	@DisplayName("Deberia retornar estado 400 cuando los datos ingresados sean invalidos")
	@WithMockUser
	void testAgendarEscenario1() throws Exception {
		
		  //GIVEN //WHEN 
		var response = mvc.perform(MockMvcRequestBuilders.post("/consultas"))
		  .andReturn().getResponse();
		  
		  //then
		  assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		 
	}
	
	@Test
	@DisplayName("Deberia retornar estado 200 cuando los datos ingresados sean invalidos")
	@WithMockUser
	void testAgendarEscenario2() throws Exception {
		//GIVEN 
		var fecha = LocalDateTime.now().plusHours(1); var especialidad =
		  Especialidad.CARDIOLOGIA; var datos = new DatosDetalleConsulta(null, 2l, 5l,
		  fecha);
		  
		  //WHEN 
		  when(agendaDeConsultaService.agendar(any())).thenReturn(datos);
		  
		  var response = mvc.perform(MockMvcRequestBuilders.post("/consultas")
		  .contentType(MediaType.APPLICATION_JSON)
		  .content(agendarConsultaJacksonTester.write(new
		  DatosAgendarConsulta(null,2l,5l,fecha, especialidad)).getJson())
		  ).andReturn().getResponse();
		  
		  //then 
		  assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		  
		  var jsonEsperado = detalleConsultaJacksonTester.write(datos).getJson();
		  
		  assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
		 
	}

}
