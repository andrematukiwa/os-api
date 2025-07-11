package com.andre.os.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.andre.os.domain.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

    @Query("SELECT obj FROM Tecnico obj WHERE obj.cpf =:cpf")
    Optional<Tecnico> findByCpf(@Param("cpf") String cpf);
}