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

import com.modelmapper.crud.dto.FuncionarioDTO;
import com.modelmapper.crud.entities.Funcionario;
import com.modelmapper.crud.repositories.FuncionarioRepository;
import com.modelmapper.crud.services.exceptions.DatabaseException;
import com.modelmapper.crud.services.exceptions.ResourceNotFoundException;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repository;
	
	@Transactional
	public FuncionarioDTO insert(FuncionarioDTO dto) {
		Funcionario entity = new Funcionario();
		entity.setNome(dto.getNome());
		entity = repository.save(entity);
		return new FuncionarioDTO(entity);
	}

	@Transactional
	public FuncionarioDTO update(Long id, FuncionarioDTO dto) {
		try {
			Funcionario entity = repository.getById(id);
			entity.setNome(dto.getNome());
			entity = repository.save(entity);
			return new FuncionarioDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Funcionario não existe: " + id);
		}
	}

	@Transactional(readOnly = true)
	public List<FuncionarioDTO> findAll() {
		List<Funcionario> obj = repository.findAll(Sort.by("nome"));
		return obj.stream().map(FuncionarioDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public FuncionarioDTO findById(Long id) {
		Funcionario obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Funcionario não existe: " + id));
		return new FuncionarioDTO(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Funcionario não existe: " + id);

		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Funcionario não existe: " + id);
		}
	}
}
