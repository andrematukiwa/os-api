package com.andre.os.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.andre.os.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

	@Query("SELECT obj FROM Pessoa obj WHERE obj.cpf = :cpf")
	Optional<Pessoa> findByCpf(@Param("cpf") String cpf);

}
