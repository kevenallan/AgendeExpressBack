package br.edu.ifpb.agendeexpress.AgendeExpress.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Cliente;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Empresa;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long>{

	Horario findByDatahora(LocalDateTime datahora);
	
	@Query(value="SELECT * FROM Horario WHERE EXTRACT(year FROM dataHora) = EXTRACT(year FROM ?1) and EXTRACT(month FROM dataHora) = EXTRACT(month FROM ?1) and EXTRACT(day FROM dataHora) = EXTRACT(day FROM ?1) and empresa_id = ?2",nativeQuery = true)
	List<Horario> listarPorDia(LocalDateTime dia, Long idEmpresa);

	List<Horario> findByCliente(Cliente cliente);
}
