package med.voll.api.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.DatosDireccionPaciente;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DireccionPacientes {
	
	private String urbanizacion;
	private String distrito;
	private String codigoPostal;
	private String complemento;
	private String numero;
	private String provincia;
	private String ciudad;
	
	public DireccionPacientes(DatosDireccionPaciente direccion) {
		this.urbanizacion = direccion.urbanizacion();
		this.distrito = direccion.distrito();
		this.codigoPostal = direccion.codigoPostal();
		this.complemento = direccion.complemento();
		this.numero = direccion.numero();
		this.provincia = direccion.provincia();
		this.ciudad = direccion.ciudad();
	}

	public void actualizarInformacion(DatosDireccionPaciente direccion) {
		this.urbanizacion = direccion.urbanizacion();
		this.distrito = direccion.distrito();
		this.codigoPostal = direccion.codigoPostal();
		this.complemento = direccion.complemento();
		this.numero = direccion.numero();
		this.provincia = direccion.provincia();
		this.ciudad = direccion.ciudad();
		
	}

}
