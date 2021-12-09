package br.edu.ifpb.agendeexpress.AgendeExpress.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome da empresa não pode ser nulo")
	@Column(unique = true)
	private String nome;
	
	@NotBlank(message = "O cnpj da empresa não pode ser nulo")
	@Column(unique = true)
	@CNPJ
	private String cnpj;
	
	@NotBlank(message = "A senha não pode ser nula")
	private String senha;
}
