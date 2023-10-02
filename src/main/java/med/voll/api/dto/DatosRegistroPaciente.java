package med.voll.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DatosRegistroPaciente(
		@NotBlank(message = "Nombre es obligatorio")
		String nombre,
		@NotBlank(message = "Email es obligatorio")
		@Email
		String email, 
		@NotBlank(message = "Telefono es obligatorio")
		String telefono, 
		@NotBlank(message = "Documento Id es obligatorio")
		//@Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}")
		String documentoIdentidad,
		@NotNull
		@Valid
		DatosDireccionPaciente direccion){

}
