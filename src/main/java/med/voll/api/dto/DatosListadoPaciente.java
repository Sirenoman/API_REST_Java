package med.voll.api.dto;

import med.voll.api.model.Paciente;

public record DatosListadoPaciente(
		Long id,
		String nombre,
		String email,
		String telefono,
		String documentoIdentidad) {
	
	public DatosListadoPaciente(Paciente paciente) {
		this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(), paciente.getDocumentoId());
	}

}
