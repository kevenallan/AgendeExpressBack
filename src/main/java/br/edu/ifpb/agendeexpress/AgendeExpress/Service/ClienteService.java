package br.edu.ifpb.agendeexpress.AgendeExpress.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.agendeexpress.AgendeExpress.DTO.ClienteAtualizarDTO;
import br.edu.ifpb.agendeexpress.AgendeExpress.DTO.ClienteCadastrarDTO;
import br.edu.ifpb.agendeexpress.AgendeExpress.DTO.ClienteLoginDTO;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Cliente;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.ClienteEmpresa;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Empresa;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Horario;
import br.edu.ifpb.agendeexpress.AgendeExpress.Repository.ClienteEmpresaRepository;
import br.edu.ifpb.agendeexpress.AgendeExpress.Repository.ClienteRepository;
import br.edu.ifpb.agendeexpress.AgendeExpress.Repository.EmpresaRepository;
import br.edu.ifpb.agendeexpress.AgendeExpress.Repository.HorarioRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private ClienteEmpresaRepository clienteEmpresaRepository;
	
	@Autowired
	private HorarioRepository horarioRepository;
	
	public Cliente cadastrar(ClienteCadastrarDTO dto) {
		Cliente clienteEmailExistente = clienteRepository.findByEmail(dto.getEmail());

		if (clienteEmailExistente != null) {
			return null;
		}
		Cliente clienteUsuarioExistente = clienteRepository.findByUsuario(dto.getUsuario());
		
		if (clienteUsuarioExistente != null) {
			return null;
		}
		
		Cliente cliente = clienteRepository.save(Cliente.builder()
				.email(dto.getEmail())
				.nome(dto.getNome())
				.usuario(dto.getUsuario())
				.senha(dto.getSenha())
				.telefone(dto.getTelefone())
				.build());
		
		for (Long id : dto.getIdEmpresa()) {
			Optional<Empresa> empresa = empresaRepository.findById(id);
			if (empresa == null) {
				return null;
			}
			ClienteEmpresa clienteEmpresa = ClienteEmpresa.builder()
					.cliente(cliente)
					.empresa(empresa.get())
					.build();
			this.clienteEmpresaRepository.save(clienteEmpresa);
		}
		
		return cliente;
	}

	@Transactional
	public Boolean atualizar(ClienteAtualizarDTO dto) {
		Cliente clienteExistente = clienteRepository.getById(dto.getId());

		if (clienteExistente == null) {
			return false;
		}
		if (dto.getEmail() != null) {
			clienteExistente.setEmail(dto.getEmail());
		}
		if (dto.getNome() != null) {
			clienteExistente.setNome(dto.getNome());						
		}
		if (dto.getUsuario() != null) {
			clienteExistente.setUsuario(dto.getUsuario());
		}
		if (dto.getSenha() != null) {
			clienteExistente.setSenha(dto.getSenha());
		}
		if (dto.getTelefone() != null) {
			clienteExistente.setTelefone(dto.getTelefone());
		}
		clienteRepository.save(clienteExistente);
		
		return true;
	}

	public Boolean apagar(Long id) {
		Cliente clienteExistente = clienteRepository.getById(id);
		
		if (clienteExistente != null) {
			List<ClienteEmpresa> clienteEmpresa = clienteEmpresaRepository.findByCliente(clienteExistente);
			for(ClienteEmpresa clienteEmp : clienteEmpresa) {
				this.clienteEmpresaRepository.delete(clienteEmp);
			}
			List<Horario> horarios = this.horarioRepository.findByCliente(clienteExistente);
			for(Horario hr : horarios) {
				this.horarioRepository.delete(hr);
			}
			clienteRepository.delete(clienteExistente);
			return true;
		}
		
		return false;
	}

	public Cliente pesquisarPorId(Long id) {
		Optional<Cliente> clienteAtualizar = clienteRepository.findById(id);
		if (!clienteAtualizar.isPresent()) {
			return null;		
		}
		return Cliente.builder()
				.id(clienteAtualizar.get().getId())
				.email(clienteAtualizar.get().getEmail())
				.nome(clienteAtualizar.get().getNome())
				.telefone(clienteAtualizar.get().getTelefone())
				.usuario(clienteAtualizar.get().getUsuario())
				.senha(clienteAtualizar.get().getSenha())
				.build();
	}

	public Cliente login(ClienteLoginDTO dto) {
		Cliente cliente = this.clienteRepository.findByUsuarioAndSenha(dto.getNomeUsuario(), dto.getSenha());
		if (cliente == null) {
			return null;			
		}
		return cliente;
	}
	
}
