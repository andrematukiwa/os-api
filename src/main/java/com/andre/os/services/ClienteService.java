package com.andre.os.services;

import com.andre.os.domain.Cliente;
import com.andre.os.dtos.ClienteDTO;
import com.andre.os.repositories.ClienteRepository;
import com.andre.os.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    public List<ClienteDTO> findAll() {
        List<Cliente> list = repository.findAll();
        return list.stream().map(ClienteDTO::new).collect(Collectors.toList());
    }

    public Cliente create(ClienteDTO objDTO) {
        Cliente obj = new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
        return repository.save(obj);
    }

    public Cliente update(Integer id, ClienteDTO objDTO) {
        Cliente obj = findById(id);
        obj.setNome(objDTO.getNome());
        obj.setCpf(objDTO.getCpf());
        obj.setTelefone(objDTO.getTelefone());
        return repository.save(obj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        repository.deleteById(id);
    }
}
