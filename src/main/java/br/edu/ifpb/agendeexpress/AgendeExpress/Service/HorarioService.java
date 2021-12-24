package br.edu.ifpb.agendeexpress.AgendeExpress.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
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
	
	public Boolean cadastrar(HorarioCadastrarDTO horario) {
		
		Optional<Cliente> cliente = this.clienteRepository.findById(horario.getIdCliente());
		Optional<Empresa> empresa = this.empresaRepository.findById(horario.getIdEmpresa());
		List<Horario> horarios = this.horarioRepository.buscarHorariosPorClienteEmpresa(horario.getDataHora().getYear(), horario.getDataHora().getMonthValue(), horario.getDataHora().getDayOfMonth(), empresa.get(), cliente.get());
		if (horarios.size()>=2) {
			return false;
		}
		horarioRepository.save(Horario.builder()
				.datahora(horario.getDataHora())
				.cliente(cliente.get())
				.empresa(empresa.get())
				.build());
		return true;		
	}
	
	public List<HorarioListarDTO> listar(LocalDateTime dataHora, Long idEmpresa){
		Optional<Empresa> empresa = this.empresaRepository.findById(idEmpresa);
		List<Horario> horarios = this.horarioRepository.listarPorDia(dataHora.getYear(),dataHora.getMonthValue(),dataHora.getDayOfMonth(), empresa.get());
		
		return this.convertDTO(horarios);
	}

	public List<String> filtrar(LocalDateTime data , Long idEmpresa) {
		LocalDateTime horaHoje = LocalDateTime.now();
		Optional<Empresa> empresa = this.empresaRepository.findById(idEmpresa);
		
		if (data.isBefore(horaHoje)) {
			return null;
		}
		
		List<Horario> horarios = this.horarioRepository.listarPorDia(data.getYear(),data.getMonthValue(),data.getDayOfMonth(), empresa.get());
		
		List<String> horariosMarcados = new ArrayList<>();
		for(Horario h: horarios){
			horariosMarcados.add(Integer.toString(h.getDatahora().getHour()));
		}
		
		List<String> horas = new ArrayList<>();
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

	public List<HorarioListarDTO> apagar(LocalDateTime dataHora, Long idCliente, Long idEmpresa){
		Optional<Cliente> cliente = this.clienteRepository.findById(idCliente);
		Optional<Empresa> empresa = this.empresaRepository.findById(idEmpresa);
		Horario horario = this.horarioRepository.findByDatahoraAndClienteAndEmpresa(dataHora, cliente.get(), empresa.get());
		if (horario == null) {
			return null;
		}
		this.horarioRepository.delete(horario);
		
		return this.buscarHorarioPorCliente(idCliente, idEmpresa);
	}

	public List<HorarioListarDTO> buscarHorarioPorCliente(Long idCliente, Long idEmpresa) {
		Optional<Cliente> cliente = this.clienteRepository.findById(idCliente);
		Optional<Empresa> empresa = this.empresaRepository.findById(idEmpresa);
		
		List<Horario> horarios = new ArrayList<>();
		if(cliente.isPresent() && empresa.isPresent()) {
			horarios = this.horarioRepository.findByClienteAndEmpresa(cliente.get(), empresa.get()); 
		}
		
		return this.convertDTO(horarios);
	}
	
	private List<HorarioListarDTO> convertDTO (List<Horario> horarios) {
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
}
