package med.voll.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarPaciente(
		@NotNull Long id,
		String nombre,
		String telefono,
		@Valid DatosDireccionPaciente direccion) {

}
