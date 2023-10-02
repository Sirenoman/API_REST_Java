package med.voll.api.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(@NotBlank String login,@NotBlank String clave) {

}
