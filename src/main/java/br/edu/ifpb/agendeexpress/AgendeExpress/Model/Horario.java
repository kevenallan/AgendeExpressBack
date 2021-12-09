package br.edu.ifpb.agendeexpress.AgendeExpress.Model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Horario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(unique = true)
	private LocalDateTime datahora;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	@NotNull
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="empresa_id")
	@NotNull
	private Empresa empresa;
}
