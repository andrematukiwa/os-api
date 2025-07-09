package com.andre.os.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.os.domain.Pessoa;
import com.andre.os.domain.Tecnico;
import com.andre.os.dtos.TecnicoDTO;
import com.andre.os.repositories.PessoaRepository;
import com.andre.os.repositories.TecnicoRepository;
import com.andre.os.services.exceptions.DataIntegratyViolationException;
import com.andre.os.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;
    
    @Autowired
    private PessoaRepository pessoaRepository;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id : " + id + ", Tipo: " + Tecnico.class.getName()));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
    	if (findByCPF(objDTO.getCpf()).isPresent()) {
    	    throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
    	}

        return repository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
    }

    public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
        Tecnico oldObj = findById(id);

        Optional<Tecnico> tecnicoOpt = repository.findByCpf(objDTO.getCpf());

        if (tecnicoOpt.isPresent() && !tecnicoOpt.get().getId().equals(id)) {
            throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
        }

        oldObj.setNome(objDTO.getNome());
        oldObj.setCpf(objDTO.getCpf());
        oldObj.setTelefone(objDTO.getTelefone());
        return repository.save(oldObj);
    }

	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui ordens de serviço não podem ser deletado!");
		}
		repository.deleteById(id);
		
	}
	
	private Optional<Pessoa> findByCPF(String cpf) {
	    return pessoaRepository.findByCpf(cpf);
	}

}
