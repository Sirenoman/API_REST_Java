package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.DatosActualizarPaciente;
import med.voll.api.dto.DatosListadoPaciente;
import med.voll.api.dto.DatosRegistroPaciente;
import med.voll.api.model.Paciente;
import med.voll.api.repositories.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@PostMapping
	@Transactional
	public void registrar(@RequestBody @Valid DatosRegistroPaciente datos) {
		pacienteRepository.save(new Paciente(datos));
	}
	
	@GetMapping
	public Page<DatosListadoPaciente> listar(@PageableDefault(page=0, size=10, sort={"nombre"}) Pageable paginacion){
		//return pacienteRepository.findAll(paginacion).map(DatosListadoPaciente::new);
		return pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new);
	}
	
	@PutMapping
	@Transactional
	public void actualizar(@RequestBody @Valid DatosActualizarPaciente datos) {
		Paciente paciente = pacienteRepository.getReferenceById(datos.id());
		paciente.actualizarInformacion(datos);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public void remover(@PathVariable Long id) {
		Paciente paciente = pacienteRepository.getReferenceById(id);
		paciente.inactivar();
	}

}
