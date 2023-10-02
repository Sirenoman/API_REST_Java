package med.voll.api.utils;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.dto.DatosCancelamientoConsulta;
import med.voll.api.repositories.ConsultaRepository;

@Component("ValidadorHorarioAntecedenciaCancelamiento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamientoConsulta{
	
	private ConsultaRepository repository;
	
	public void validar(DatosCancelamientoConsulta datos) {
		var consulta = repository.getReferenceById(datos.idConsulta());
		var ahora = LocalDateTime.now();
		var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();
		
		if(diferenciaEnHoras < 24) {
			throw new ValidationException("Consulta solamente puede ser cancelada"
					+ " en un intervalo menor a 24 hrs");
		}
	}
}
