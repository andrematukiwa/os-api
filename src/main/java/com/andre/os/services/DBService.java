package com.andre.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.os.domain.Cliente;
import com.andre.os.domain.OS;
import com.andre.os.domain.Tecnico;
import com.andre.os.domain.enuns.Prioridade;
import com.andre.os.domain.enuns.Status;
import com.andre.os.repositories.ClienteRepository;
import com.andre.os.repositories.OSRepository;
import com.andre.os.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	public void instanciaDB() {
	    Tecnico t1 = new Tecnico(null, "Valdir", "11144477735", "41 99193-5890");
	    Tecnico t2 = new Tecnico(null, "Valter", "11144477730", "41 99193-5890");
	    Cliente c1 = new Cliente(null, "Betina Campos", "59850820010",  "41 99193-5899");

	    OS os1 = new OS(null, null, Prioridade.ALTA, "Teste de ordem de serviço", Status.ANDAMENTO, t1, c1);	

	    tecnicoRepository.saveAll(Arrays.asList(t1, t2));
	    clienteRepository.saveAll(Arrays.asList(c1));
	    osRepository.saveAll(Arrays.asList(os1));
	}

}
