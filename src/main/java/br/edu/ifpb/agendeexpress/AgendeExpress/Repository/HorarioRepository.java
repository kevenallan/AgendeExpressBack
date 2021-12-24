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
	
	@Query("SELECT h FROM Horario h "
			+ "WHERE DATE_PART('year', h.datahora) = :ano "
			+ "and DATE_PART('month', h.datahora) = :mes "
			+ "and DATE_PART('day', h.datahora) = :dia "
			+ "and h.empresa = :idEmpresa "
			+ "order by h.datahora")
	List<Horario> listarPorDia(int ano,int mes,int dia, Empresa idEmpresa);

	List<Horario> findByCliente(Cliente cliente);
	
	Horario findByDatahoraAndClienteAndEmpresa(LocalDateTime dataHora, Cliente idCliente, Empresa idEmpresa);
	
	List<Horario> findByClienteAndEmpresa(Cliente cliente, Empresa empresa);
}
