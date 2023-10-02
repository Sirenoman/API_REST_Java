package med.voll.api.utils;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.dto.DatosAgendarConsulta;

@Component
public class HorarioDeFuncioamientoClinica implements ValidadorDeConsultas{
	
	public void validar(DatosAgendarConsulta datos) {
		
		var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
		var antesDeApertura = datos.fecha().getHour()<7;
		var despuesdeCierre = datos.fecha().getHour()>19;
		if(domingo || antesDeApertura || despuesdeCierre) {
			throw new ValidationException("El horario de atencion de la clinica es de lunes a "
					+ "sabado de 7 am a 7 pm");
		}
	}
	

}
