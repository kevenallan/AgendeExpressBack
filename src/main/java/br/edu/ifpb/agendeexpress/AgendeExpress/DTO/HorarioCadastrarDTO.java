package br.edu.ifpb.agendeexpress.AgendeExpress.DTO;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class HorarioCadastrarDTO {

	private LocalDateTime dataHora;
	
	private Long idCliente;
	
	private Long idEmpresa;
	
}
