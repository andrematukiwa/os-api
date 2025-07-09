package com.andre.os.services;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.os.domain.Cliente;
import com.andre.os.domain.OS;
import com.andre.os.domain.Tecnico;
import com.andre.os.domain.enuns.Prioridade;
import com.andre.os.domain.enuns.Status;
import com.andre.os.dtos.OSDTO;
import com.andre.os.repositories.OSRepository;
import com.andre.os.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class OsService {

	@Autowired
	private OSRepository repository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;
	
	public OS findById(Integer id) {
		Optional<OS> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + OS.class.getName()));
	}
	
	public List<OS> findAll(){
		return repository.findAll();
		
	}

	public OS create(@Valid OSDTO obj) {
		return fromDTO(obj);
	}
	
	public OS update(Integer id, @Valid OSDTO obj) {
	    OS existing = findById(id); 
	    existing.setObservacoes(obj.getObservacoes());
	    existing.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
	    existing.setStatus(Status.toEnum(obj.getStatus()));

	    Tecnico tec = tecnicoService.findById(obj.getTecnico());
	    Cliente cli = clienteService.findById(obj.getCliente());

	    existing.setTecnico(tec);
	    existing.setCliente(cli);

	    if (existing.getStatus().getCod().equals(2)) { // FECHADO
	        existing.setDataFechamento(LocalDateTime.now());
	    }

	    return repository.save(existing);
	}


	
	private OS fromDTO(OSDTO obj) {
		OS newObj = new OS();
		newObj.setId(obj.getId());
		newObj.setDataAbertura(LocalDateTime.now());
		newObj.setObservacoes(obj.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		newObj.setStatus(Status.toEnum(obj.getStatus()));
		
		Tecnico tec = tecnicoService.findById(obj.getTecnico());
		Cliente cli = clienteService.findById(obj.getCliente());
		
		newObj.setTecnico(tec);
		newObj.setCliente(cli);
		
		if(newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now()); 
		}
		
		return repository.save(newObj);
	}
}
