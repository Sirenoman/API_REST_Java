package med.voll.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import med.voll.api.dto.DatosListadoMedico;
import med.voll.api.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{

	Page<Medico> findByActivoTrue(Pageable paginacion);

}
