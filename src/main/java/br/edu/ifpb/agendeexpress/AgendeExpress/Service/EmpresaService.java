package br.edu.ifpb.agendeexpress.AgendeExpress.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.agendeexpress.AgendeExpress.DTO.EmpresaAtualizarDTO;
import br.edu.ifpb.agendeexpress.AgendeExpress.DTO.EmpresaLoginDTO;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Cliente;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.ClienteEmpresa;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Empresa;
import br.edu.ifpb.agendeexpress.AgendeExpress.Repository.ClienteEmpresaRepository;
import br.edu.ifpb.agendeexpress.AgendeExpress.Repository.EmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private ClienteEmpresaRepository clienteEmpresaRepository;
	
	public Empresa cadastrar(Empresa empresa) {
		Empresa empresaExistente = empresaRepository.findByCnpj(empresa.getCnpj());
		
		if(empresaExistente == null) {
			Empresa empresaCadastrada = this.empresaRepository.save(empresa);
			return empresaCadastrada;
		}
		
		return null;
	}

	public Boolean apagar(Long id) {
		Empresa empresaExistente = empresaRepository.getById(id);
		if(empresaExistente != null) {
			List<ClienteEmpresa> clienteEmpresa = this.clienteEmpresaRepository.findByEmpresa(empresaExistente);
			for (ClienteEmpresa clienteEmp: clienteEmpresa) {
				this.clienteEmpresaRepository.delete(clienteEmp);
			}
			this.empresaRepository.delete(empresaExistente);
			return true;
		}
		
		return false;
	}

	@Transactional
	public Boolean atualizar(EmpresaAtualizarDTO dto) {		
		Empresa empresaExistente = empresaRepository.getById(dto.getId());
		
		if(empresaExistente != null) {
			if(dto.getNome() != null)
			 empresaExistente.setNome(dto.getNome());
			if(dto.getSenha() != null)
				empresaExistente.setSenha(dto.getSenha());
			this.empresaRepository.save(empresaExistente);
			return true;
		}
		
		return false;
	}

	public EmpresaAtualizarDTO pesquisarPorId(Long id) {
		Optional<Empresa> empresa = empresaRepository.findById(id);
		if (!empresa.isPresent()) {
			return null;		
		}
		return EmpresaAtualizarDTO.builder()
				.id(empresa.get().getId())
				.nome(empresa.get().getNome())
				.senha(empresa.get().getSenha())
				.build();
		
	}

	public Empresa login(EmpresaLoginDTO dto) {
		Empresa empresa = this.empresaRepository.findByCnpjAndSenha(dto.getCnpj(), dto.getSenha());
		if (empresa == null) {
			return null;			
		}
		return empresa;
	}
}
