package med.voll.api.dto;

import med.voll.api.model.Direccion;
import med.voll.api.model.Medico;

public record DatosDetalladoMedico(Long id, String nombre, String documento, Direccion direccion) {
	
	public DatosDetalladoMedico(Medico medico) {
		this(medico.getId(), medico.getNombre(), medico.getDocumento(), medico.getDireccion());
	}

}
