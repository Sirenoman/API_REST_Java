package med.voll.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.dto.DatosAgendarConsulta;
import med.voll.api.repositories.MedicoRepository;

@Component
public class MedicoActivo implements ValidadorDeConsultas{

	@Autowired
	private MedicoRepository repository;
	
	public void validar(DatosAgendarConsulta datos) {
		if(datos.idMedico() == null) {
			return;
		}
		
		var medicoActivo = repository.findActivoById(datos.idMedico());
		if(!medicoActivo) {
			throw new ValidationException("No se permite agendar con medicos inactivos");
		}
	}	

}
