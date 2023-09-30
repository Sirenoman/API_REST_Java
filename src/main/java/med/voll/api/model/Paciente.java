package med.voll.api.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.DatosActualizarPaciente;
import med.voll.api.dto.DatosRegistroPaciente;

@Entity(name="Paciente")
@Table(name="pacientes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of="id")
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String email;
	private String documentoId;
	private String telefono;
	private Boolean activo;
	@Embedded
	private DireccionPacientes direccion;
	
	public Paciente(DatosRegistroPaciente datos) {
		this.activo = true;
		this.nombre = datos.nombre();
		this.email = datos.email();
		this.documentoId = datos.documentoIdentidad();
		this.telefono = datos.telefono();
		this.direccion = new DireccionPacientes(datos.direccion());
	}

	public void actualizarInformacion(@Valid DatosActualizarPaciente datos) {
		if(datos.nombre() != null) {
			this.nombre = datos.nombre();
		}
		if(datos.telefono() != null) {
			this.telefono = datos.telefono();
		}
		if(datos.direccion() != null) {
			direccion.actualizarInformacion(datos.direccion());
		}
	}
	
	public void inactivar() {
		this.activo = false;
	}
}
