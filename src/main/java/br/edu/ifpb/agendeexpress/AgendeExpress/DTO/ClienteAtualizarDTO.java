package br.edu.ifpb.agendeexpress.AgendeExpress.DTO;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteAtualizarDTO {

	private Long id;
	
	private String nome;

	@Email
	private String email;
	
	private String usuario;
	
	private String senha;

	private String telefone;
	
}
