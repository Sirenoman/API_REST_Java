package med.voll.api.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosDireccionPaciente(
		@NotBlank
		String urbanizacion,
		@NotBlank
		String distrito,
		@NotBlank String codigoPostal,
		@NotBlank String complemento,
		@NotBlank String numero,
		@NotBlank String provincia,
		@NotBlank String ciudad
		) {

}
