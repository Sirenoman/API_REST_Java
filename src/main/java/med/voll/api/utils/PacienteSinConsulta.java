package med.voll.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.dto.DatosAgendarConsulta;
import med.voll.api.repositories.ConsultaRepository;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{
	
	@Autowired
	private ConsultaRepository repository;
	
	public void validar(DatosAgendarConsulta datos) {
		var primerHorario = datos.fecha().withHour(7);
		var ultimoHorario = datos.fecha().withHour(18);
		
		var pacienteConConsulta = repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHorario, 
				ultimoHorario);
		
		if(pacienteConConsulta) {
			throw new ValidationException("El paciente ya tiene una consulta para ese dia");
		}
	}

}
