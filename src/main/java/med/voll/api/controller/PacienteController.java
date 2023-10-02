package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.DatosActualizarPaciente;
import med.voll.api.dto.DatosDetalladoPaciente;
import med.voll.api.dto.DatosListadoPaciente;
import med.voll.api.dto.DatosRegistroPaciente;
import med.voll.api.model.Paciente;
import med.voll.api.repositories.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity registrar(@RequestBody @Valid DatosRegistroPaciente datos,
			UriComponentsBuilder uriBuilder) {
		var paciente = new Paciente(datos);
		pacienteRepository.save(paciente);
		
		var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
		return ResponseEntity.created(uri).body(new DatosDetalladoPaciente(paciente));
	}
	
	@GetMapping
	public ResponseEntity<Page<DatosListadoPaciente>> listar(@PageableDefault(page=0, size=10, sort={"nombre"}) Pageable paginacion){
		//return pacienteRepository.findAll(paginacion).map(DatosListadoPaciente::new);
		return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity actualizar(@RequestBody @Valid DatosActualizarPaciente datos) {
		Paciente paciente = pacienteRepository.getReferenceById(datos.id());
		paciente.actualizarInformacion(datos);
		
		return ResponseEntity.ok(new DatosDetalladoPaciente(paciente));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity remover(@PathVariable Long id) {
		Paciente paciente = pacienteRepository.getReferenceById(id);
		paciente.inactivar();
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity detallar(@PathVariable Long id) {
		Paciente paciente = pacienteRepository.getReferenceById(id);
		return ResponseEntity.ok(new DatosDetalladoPaciente(paciente));
	}

}
