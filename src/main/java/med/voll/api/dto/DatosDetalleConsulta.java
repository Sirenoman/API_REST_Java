package med.voll.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import med.voll.api.model.Consulta;

public record DatosDetalleConsulta(
		Long id,
		Long idPaciente,
		Long idMedico,
		@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
		LocalDateTime fecha) {

	public DatosDetalleConsulta(Consulta consulta) {
		this(consulta.getId(), consulta.getPaciente().getId(), consulta.getMedico().getId(),
				 consulta.getFecha());
	}

}
