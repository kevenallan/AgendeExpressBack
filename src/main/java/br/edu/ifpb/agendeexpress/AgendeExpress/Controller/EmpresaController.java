package br.edu.ifpb.agendeexpress.AgendeExpress.Controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.agendeexpress.AgendeExpress.DTO.ClienteLoginDTO;
import br.edu.ifpb.agendeexpress.AgendeExpress.DTO.EmpresaAtualizarDTO;
import br.edu.ifpb.agendeexpress.AgendeExpress.DTO.EmpresaLoginDTO;
import br.edu.ifpb.agendeexpress.AgendeExpress.Model.Empresa;
import br.edu.ifpb.agendeexpress.AgendeExpress.Repository.EmpresaRepository;
import br.edu.ifpb.agendeexpress.AgendeExpress.Service.EmpresaService;

@RestController
@RequestMapping(value = "/empresa")
//@CrossOrigin(origins = "https://agende-express-front.herokuapp.com")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@GetMapping("/listar")
	public List<Empresa> getEmpresas() {
		return this.empresaRepository.findAll();
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Empresa> cadastrar(@RequestBody Empresa empresa){
		return ResponseEntity.ok(this.empresaService.cadastrar(empresa)); 
	}
	
	@DeleteMapping("/apagar")
	public ResponseEntity<Boolean> apagar(@RequestParam @NotNull(message = "O id da empresa não pode ser nulo") Long id){
		return ResponseEntity.ok(this.empresaService.apagar(id)); 
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Boolean> atualizar(@RequestBody EmpresaAtualizarDTO dto){
		return ResponseEntity.ok(this.empresaService.atualizar(dto)); 
	}
	
	@GetMapping("/pesquisarPorId")
	public EmpresaAtualizarDTO pesquisarPorId(@RequestParam Long id) {
		return this.empresaService.pesquisarPorId(id);
	}
	
	@PostMapping("/login")
	public Empresa login(@RequestBody EmpresaLoginDTO dto) {
		return this.empresaService.login(dto);
	}
}
