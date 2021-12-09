package br.edu.ifpb.agendeexpress.AgendeExpress.DTO;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaAtualizarDTO {

	@NotNull
	private Long id;
	
//	@JsonProperty(required = false)
	private String nome;
	
//	@JsonProperty(required = false)
	private String senha;
}
