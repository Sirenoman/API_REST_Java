package med.voll.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.dto.DatosAgendarConsulta;
import med.voll.api.dto.DatosCancelamientoConsulta;
import med.voll.api.dto.DatosDetalleConsulta;
import med.voll.api.model.Consulta;
import med.voll.api.model.Medico;
import med.voll.api.model.Paciente;
import med.voll.api.repositories.ConsultaRepository;
import med.voll.api.repositories.MedicoRepository;
import med.voll.api.repositories.PacienteRepository;
import med.voll.api.utils.ValidacionDeIntegridad;
import med.voll.api.utils.ValidadorCancelamientoConsulta;
import med.voll.api.utils.ValidadorDeConsultas;

import java.util.List;

@Service
public class AgendaDeConsultaService {
	
	@Autowired
	private PacienteRepository pacienteRepo;
	
	@Autowired
	private MedicoRepository medicoRepo;
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	List<ValidadorDeConsultas> validadores;
	
	@Autowired
	List<ValidadorCancelamientoConsulta> validadoresCancelamiento;
	
	public DatosDetalleConsulta agendar(DatosAgendarConsulta datos) {
		
		if(!pacienteRepo.findById(datos.idPaciente()).isPresent()) {
			throw new ValidacionDeIntegridad("Este id para paciente no fue encontrado");
		}
		
		if(datos.idMedico() != null && !medicoRepo.existsById(datos.idMedico())) {
			throw new ValidacionDeIntegridad("Este id para medico no fue encontrado");
		}
		
		// VALIDACIONES
		validadores.forEach(v -> v.validar(datos));
		
		var paciente = pacienteRepo.findById(datos.idPaciente()).get();
		var medico = seleccionarMedico(datos);
		
		if(medico==null) {
			throw new ValidacionDeIntegridad("No se encuentra medico disponible para este horario y especialidad");
		}
		
		var consulta = new Consulta(medico, paciente, datos.fecha());
		consultaRepository.save(consulta);
		
		return new DatosDetalleConsulta(consulta);
	}
	
	public void cancelar(DatosCancelamientoConsulta datos) {
		if(!consultaRepository.existsById(datos.idConsulta())) {
			throw new ValidacionDeIntegridad("Id de la consulta informada no existe");
		}
		
		validadoresCancelamiento.forEach(v -> v.validar(datos));
		
		var consulta = consultaRepository.getReferenceById(datos.idConsulta());
		consulta.cancelar(datos.motivo());
	}

	private Medico seleccionarMedico(DatosAgendarConsulta datos) {
		if(datos.idMedico()!= null) {
			return medicoRepo.getReferenceById(datos.idMedico());
		}
		if(datos.especialidad()==null) {
			throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el medico");
		}
		return medicoRepo.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
	}

}
