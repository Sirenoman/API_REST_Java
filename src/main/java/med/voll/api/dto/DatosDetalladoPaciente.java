package med.voll.api.dto;

import med.voll.api.model.DireccionPacientes;
import med.voll.api.model.Paciente;

public record DatosDetalladoPaciente(String nombre, String email, String telefono, String documentoIdentidad,
			DireccionPacientes direccion) {
	
	public DatosDetalladoPaciente(Paciente paciente) {
		this(paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(), paciente.getDocumentoId(),
					paciente.getDireccion());
	}

}
