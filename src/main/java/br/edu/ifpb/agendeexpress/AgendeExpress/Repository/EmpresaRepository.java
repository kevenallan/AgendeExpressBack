package br.edu.ifpb.agendeexpress.AgendeExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Cliente;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

	public Empresa findByCnpj(String cnpj);

	public Empresa findByCnpjAndSenha(String cnpj, String senha);
}
