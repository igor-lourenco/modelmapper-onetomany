package com.modelmapper.crud.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.modelmapper.crud.dto.DepartamentoDTO;
import com.modelmapper.crud.entities.Departamento;
import com.modelmapper.crud.repositories.DepartamentoRepository;
import com.modelmapper.crud.services.exceptions.DatabaseException;
import com.modelmapper.crud.services.exceptions.ResourceNotFoundException;

@Service
public class DepartamentoService {

	@Autowired
	private DepartamentoRepository repository;
	@Autowired
	ModelMapper modelMapper;

	@Transactional
	public DepartamentoDTO insert(DepartamentoDTO dto) {
		Departamento entity = modelMapper.map(dto, Departamento.class);
		entity = repository.save(entity);
		return modelMapper.map(entity, DepartamentoDTO.class);
	}

	@Transactional
	public DepartamentoDTO update(Long id, DepartamentoDTO dto) {
		try {
			Departamento entity = repository.getById(id);
			 modelMapper.map(dto, Departamento.class);
			return  modelMapper.map(entity, DepartamentoDTO.class);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Departamento não existe: " + id);
		}
	}

	@Transactional(readOnly = true)
	public List<DepartamentoDTO> findAll() {
		List<Departamento> obj = repository.findAll(Sort.by("nome"));
		return obj.stream().map(d -> modelMapper.map(d, DepartamentoDTO.class)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public DepartamentoDTO findById(Long id) {
		Departamento obj = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Departamento não existe: " + id));
		return  modelMapper.map(obj, DepartamentoDTO.class);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Departamento não existe: " + id);

		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Departamento não existe: " + id);

		}
	}
}
