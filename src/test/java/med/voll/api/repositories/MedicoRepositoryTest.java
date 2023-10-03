package med.voll.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.dto.DatosDireccion;
import med.voll.api.dto.DatosDireccionPaciente;
import med.voll.api.dto.DatosRegistroMedico;
import med.voll.api.dto.DatosRegistroPaciente;
import med.voll.api.dto.Especialidad;
import med.voll.api.model.Consulta;
import med.voll.api.model.Medico;
import med.voll.api.model.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test")
class MedicoRepositoryTest {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private TestEntityManager em;

	@Test
	@DisplayName("deberia retornar nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
	void testSeleccionarMedicoConEspecialidadEnFechaEscenario1() {
		//Given
		var proximoLunes10H = LocalDate.now()
		           .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
		           .atTime(10,0);
		var medico=registrarMedico("Jose","j@mail.com","998761",Especialidad.CARDIOLOGIA);
		//var paciente= registrarPaciente("antonio","a@mail.com","654321");
		//registrarConsulta(medico,paciente,proximoLunes10H);
		//When
		var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA,proximoLunes10H);
		
		//then
		assertThat(medicoLibre).isEqualTo(medico);
	}
	
	private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
		   em.persist(new Consulta(null, medico, paciente, fecha, null));
		}
	
	private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
		   var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
		   em.persist(medico);
		   return medico;
	}
	
	private Paciente registrarPaciente(String nombre, String email, String documento) {
		   var paciente = new Paciente(datosPaciente(nombre, email, documento));
		   em.persist(paciente);
		   return paciente;
	}

	private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
		return new DatosRegistroPaciente(
				nombre, 
				email, 
				"77777777", 
				documento, 
				datosDireccionPaciente());
	}

	private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
		   return new DatosRegistroMedico(
		           nombre,
		           email,
		           "61999999999",
		           documento,
		           especialidad,
		           datosDireccion());	   
	}

	private DatosDireccion datosDireccion() {
		return new DatosDireccion(
				"calle 1",
				"distrito 1",
				"Lima",
				"1",
				"a");
	}

	private @NotNull @Valid DatosDireccionPaciente datosDireccionPaciente() {
		return new DatosDireccionPaciente("urba 1",
				"distrito 3",
				"L12233",
				"e",
				"33",
				"Colombai",
				"Lima");
	}
}
