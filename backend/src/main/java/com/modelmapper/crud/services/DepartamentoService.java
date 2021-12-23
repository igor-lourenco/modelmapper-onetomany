package com.modelmapper.crud.services;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.modelmapper.crud.dto.DepartamentoDTO;
import com.modelmapper.crud.entities.Departamento;
import com.modelmapper.crud.repositories.DepartamentoRepository;
import com.modelmapper.crud.services.exceptions.ResourceNotFoundException;

@Service
public class DepartamentoService implements GenericService<Departamento, DepartamentoDTO, Long> {

	@Autowired
	private DepartamentoRepository repository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public JpaRepository<Departamento, Long> repository() {
		return repository;
	}
	
	@Override
	public DepartamentoDTO insert(DepartamentoDTO dto) {
		Departamento entity =  new Departamento();
		entity = modelMapper.map(dto, Departamento.class);
		entity = repository.save(entity);
		return modelMapper.map(entity, DepartamentoDTO.class);
	}

	@Override
	public DepartamentoDTO update(Long id, DepartamentoDTO dto) {
		try {
			Departamento entity = repository.getById(id);
			 modelMapper.map(dto, entity);
			return  modelMapper.map(entity, DepartamentoDTO.class);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Departamento não existe: " + id);
		}
	}
}
