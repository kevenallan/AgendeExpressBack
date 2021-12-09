package br.edu.ifpb.agendeexpress.AgendeExpress.DTO;

import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ClienteCadastrarDTO {

	@NotBlank(message = "O nome do cliente não pode ser nulo")
	private String nome;

	@NotBlank(message = "O email do cliente não pode ser nulo")
	@Email
	private String email;
	
	@Column(unique = true)
	@NotBlank(message = "O usuario não pode ser nulo")
	private String usuario;
	
	@NotBlank(message = "A senha não pode ser nula")
	private String senha;

	private String telefone;
	
	@NotNull
	private List<Long> idEmpresa;
	
}
