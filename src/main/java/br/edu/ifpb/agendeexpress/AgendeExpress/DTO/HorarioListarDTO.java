package br.edu.ifpb.agendeexpress.AgendeExpress.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HorarioListarDTO {

	private String dia;
	private int mes;
	private int ano;
	private String hora;
	private int minuto;
	private int diaSemana;
}
