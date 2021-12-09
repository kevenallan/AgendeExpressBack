package br.edu.ifpb.agendeexpress.AgendeExpress.DTO;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Data;

@Data
public class EmpresaLoginDTO {

	@NotBlank
	@CNPJ
	private String cnpj;
	
	@NotBlank
	private String senha;
}
