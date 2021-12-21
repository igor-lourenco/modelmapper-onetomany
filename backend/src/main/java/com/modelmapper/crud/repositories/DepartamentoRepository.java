package com.modelmapper.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modelmapper.crud.entities.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long>{

}
