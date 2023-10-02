package med.voll.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.dto.DatosAgendarConsulta;
import med.voll.api.repositories.PacienteRepository;

@Component
public class PacienteActivo implements ValidadorDeConsultas{
	
	@Autowired
	private PacienteRepository repository;
	
	public void validar(DatosAgendarConsulta datos) {
		if(datos.idPaciente() == null) {
			return;
		}
		
		var pacienteActivo = repository.findActivoById(datos.idPaciente());
		if(!pacienteActivo) {
			throw new ValidationException("No se permite agendar con paciente inactivos");
		}
	}

}
