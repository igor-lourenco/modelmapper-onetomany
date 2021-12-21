package com.modelmapper.crud.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

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
	
	@Transactional
	public DepartamentoDTO insert(DepartamentoDTO dto) {
		Departamento entity = new Departamento();
		entity.setNome(dto.getNome());
		entity = repository.save(entity);
		return new DepartamentoDTO(entity);
	}

	@Transactional
	public DepartamentoDTO update(Long id, DepartamentoDTO dto) {
		try {
			Departamento entity = repository.getById(id);
			entity.setNome(dto.getNome());
			entity = repository.save(entity);
			return new DepartamentoDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Departamento não existe: " + id);
		}
	}

	@Transactional(readOnly = true)
	public List<DepartamentoDTO> findAll() {
		List<Departamento> obj = repository.findAll(Sort.by("nome"));
		return obj.stream().map(DepartamentoDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public DepartamentoDTO findById(Long id) {
		Departamento obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Departamento não existe: " + id));
		return new DepartamentoDTO(obj);
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
