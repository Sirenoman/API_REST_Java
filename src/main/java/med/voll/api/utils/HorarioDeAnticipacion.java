package med.voll.api.utils;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.dto.DatosAgendarConsulta;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{
	
	public void validar(DatosAgendarConsulta datos) {
		var ahora = LocalDateTime.now();
		var horaDeConsulta = datos.fecha();
		
		var diferenciaDe30Min = Duration.between(ahora, horaDeConsulta).toMinutes()<30;
		
		if(diferenciaDe30Min) {
			throw new ValidationException("Las consultad debe programarse con almenos 30 min de anticipacion");
		}
	}

}
