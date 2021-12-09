package br.edu.ifpb.agendeexpress.AgendeExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Cliente findByEmail(String email);
	Cliente findByUsuario(String usuario);
	Cliente findByUsuarioAndSenha(String usuario, String senha);
	
}
