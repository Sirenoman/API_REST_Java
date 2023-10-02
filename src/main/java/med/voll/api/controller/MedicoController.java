package med.voll.api.controller;

import java.net.URI;

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

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.DatosActualizarMedico;
import med.voll.api.dto.DatosDetalladoMedico;
import med.voll.api.dto.DatosListadoMedico;
import med.voll.api.dto.DatosRegistroMedico;
import med.voll.api.model.Medico;
import med.voll.api.repositories.MedicoRepository;


@RestController
@RequestMapping("/medicos")
public class MedicoController {
	// MANERA NO MUY EFICIENTE DE LLAMAR LA INTERFAZ DE REPOSITORIO
	@Autowired
	private MedicoRepository medicoRepository;
	
	@PostMapping
	public ResponseEntity registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
				UriComponentsBuilder uriComponentsBuilder) {
		System.out.println("El request llega correctamente");
		Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
		URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(url).body(medico);
	}
	
	//Aplicando PAGINACION
	@GetMapping
	public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion) {
		// Manera de clasificar que datos quiero que muestre JSON sin utilizar JsonIgnore
		//return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
		return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
		// ESTO SE REALIZA CON UNA QUERY PERSONALIZADA
	}
	/*
	 * Manera de PAGINAR LA LISTA
	 * Mediante Query params, ejemplos:
	 * ?size=1&page=1
	 * todo esto despues de la url de peticion:
	 * ?size=10&page=2&sort=nombre(atributo registrado en la entidad)
	 * 
	 * */
	
	@PutMapping
	@Transactional
	public ResponseEntity<DatosDetalladoMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
		Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
		medico.actualizarDatos(datosActualizarMedico);
		// LA ETIQUETA TRANSACTIONAL AYUDA A APLICAR EL METODO DE PUT EN LA BASE DE DATOS 
		// YA QUE REALIZA UN ROLLBACK AL MOMENTO DE TERMINAR EL METODO DE ACTUALIZACION
		return ResponseEntity.ok(new DatosDetalladoMedico(medico));
	}
	
	// DELETE LOGICO
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity eliminarMedico(@PathVariable Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		medico.desactivarMedico();
		return ResponseEntity.noContent().build();
	}
	
	// DELETE EN BASE DE DATOS
/*	public void eliminarMedico(Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		medicoRepository.delete(medico);
	}*/
	
	@GetMapping("/{id}")
	public ResponseEntity<DatosDetalladoMedico> retornaDatosMedico(@PathVariable Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		return ResponseEntity.ok(new DatosDetalladoMedico(medico));
	}
}
