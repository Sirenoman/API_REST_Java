package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.DatosAgendarConsulta;
import med.voll.api.dto.DatosCancelamientoConsulta;
import med.voll.api.dto.DatosDetalleConsulta;
import med.voll.api.services.AgendaDeConsultaService;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
	
	@Autowired
	private AgendaDeConsultaService servicio;

	@PostMapping
	@Transactional
	public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos) {
		//System.out.println(datos);
		
		var response = servicio.agendar(datos);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping
	@Transactional
	public ResponseEntity cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos) {
		servicio.cancelar(datos);
		return ResponseEntity.noContent().build();
	}
}
