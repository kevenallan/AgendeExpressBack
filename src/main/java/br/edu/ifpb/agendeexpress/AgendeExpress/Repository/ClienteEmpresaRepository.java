package br.edu.ifpb.agendeexpress.AgendeExpress.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Cliente;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.ClienteEmpresa;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Empresa;

@Repository
public interface ClienteEmpresaRepository extends JpaRepository<ClienteEmpresa, Long> {

	List<ClienteEmpresa> findByCliente(Cliente cliente);
	
	List<ClienteEmpresa> findByEmpresa(Empresa empresa);
	
}
