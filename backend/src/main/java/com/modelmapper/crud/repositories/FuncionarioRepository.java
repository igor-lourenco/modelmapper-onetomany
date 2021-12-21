package com.modelmapper.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modelmapper.crud.entities.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

}
