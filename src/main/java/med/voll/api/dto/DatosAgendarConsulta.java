package med.voll.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record DatosAgendarConsulta(
		Long id, 
		@NotNull @JsonAlias({"id_paciente","idpaciente"}) Long idPaciente, 
		@JsonAlias({"id_medico","idmedico"}) Long idMedico, 
		@NotNull @Future LocalDateTime fecha,
		Especialidad especialidad) {

}
