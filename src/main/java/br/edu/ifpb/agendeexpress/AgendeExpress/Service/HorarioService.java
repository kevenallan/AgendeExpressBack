package br.edu.ifpb.agendeexpress.AgendeExpress.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.agendeexpress.AgendeExpress.DTO.HorarioCadastrarDTO;
import br.edu.ifpb.agendeexpress.AgendeExpress.DTO.HorarioListarDTO;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Cliente;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Empresa;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Horario;
import br.edu.ifpb.agendeexpress.AgendeExpress.Repository.ClienteRepository;
import br.edu.ifpb.agendeexpress.AgendeExpress.Repository.EmpresaRepository;
import br.edu.ifpb.agendeexpress.AgendeExpress.Repository.HorarioRepository;

@Service
public class HorarioService {

	@Autowired
	private HorarioRepository horarioRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public void cadastrar(HorarioCadastrarDTO horario) {
		Horario horarioExistente = horarioRepository.findByDatahora(horario.getDataHora());
		Optional<Cliente> cliente = clienteRepository.findById(horario.getIdCliente());
		Optional<Empresa> empresa = empresaRepository.findById(horario.getIdEmpresa());
	
		horarioRepository.save(Horario.builder()
				.datahora(horario.getDataHora())
				.cliente(cliente.get())
				.empresa(empresa.get())
				.build());
				
	}
	
	public List<HorarioListarDTO> listar(LocalDateTime dataHora, Long idEmpresa){
		List<Horario> horarios = horarioRepository.listarPorDia(dataHora, idEmpresa);
		List<HorarioListarDTO> horariosDTO = new ArrayList<>();
		
		for(Horario hr : horarios) {
			HorarioListarDTO hrDTO =  HorarioListarDTO.builder()
			.ano(hr.getDatahora().getYear())
			.mes(hr.getDatahora().getMonthValue())
			.dia(hr.getDatahora().getDayOfMonth() < 10 ? "0" + hr.getDatahora().getDayOfMonth() : "" + hr.getDatahora().getDayOfMonth())
			.hora(hr.getDatahora().getHour() < 10 ? "0" + hr.getDatahora().getHour() : "" + hr.getDatahora().getHour())
			.minuto(hr.getDatahora().getMinute())
			.diaSemana(hr.getDatahora().getDayOfWeek().getValue())
			.build();
			horariosDTO.add(hrDTO);
		}
		
		return horariosDTO;

	}

	public List<String> filtrar(LocalDateTime data , Long idEmpresa) {
		LocalDateTime horaHoje = LocalDateTime.now();
		
		List<Horario> horarios = this.horarioRepository.listarPorDia(data, idEmpresa);
		
		List<String> horariosMarcados = new ArrayList<>();
		for(Horario h: horarios){
			horariosMarcados.add(Integer.toString(h.getDatahora().getHour()));
		}
		
		List<String> horas = new ArrayList<>();
		System.out.println(data.toLocalDate());
		System.out.println(horaHoje.toLocalDate());
		if(data.toLocalDate().equals(horaHoje.toLocalDate())) {
			for(int i = 8; i < 18; i++) {
				if(!horariosMarcados.contains(Integer.toString(i))){
					if(i > horaHoje.getHour())
						horas.add(Integer.toString(i));	
				}
			}
		}
		else {
			for(int i = 8; i < 18; i++) {
				if(!horariosMarcados.contains(Integer.toString(i))){
					horas.add(Integer.toString(i));	
				}
			}
		}
		return horas;
	}
}
