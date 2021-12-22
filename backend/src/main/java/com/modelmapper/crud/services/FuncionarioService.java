package com.modelmapper.crud.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.modelmapper.crud.dto.FuncionarioDTO;
import com.modelmapper.crud.entities.Departamento;
import com.modelmapper.crud.entities.Funcionario;
import com.modelmapper.crud.repositories.DepartamentoRepository;
import com.modelmapper.crud.repositories.FuncionarioRepository;
import com.modelmapper.crud.services.exceptions.DatabaseException;
import com.modelmapper.crud.services.exceptions.ResourceNotFoundException;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repository;
	@Autowired
	private DepartamentoRepository departamentoRepository;
	
	@Transactional
	public FuncionarioDTO insert(FuncionarioDTO dto) {
		Funcionario entity = new Funcionario();
		copiaEntidade(entity, dto);
		return new FuncionarioDTO(entity);
	}

	@Transactional
	public FuncionarioDTO update(Long id, FuncionarioDTO dto) {
			Funcionario entity = repository.getById(id);
			copiaEntidade(entity, dto);
			return new FuncionarioDTO(entity);
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
	
	private void copiaEntidade(Funcionario entity, FuncionarioDTO dto) {
		try {
		entity.setNome(dto.getNome());
		Departamento departamento = departamentoRepository.getById(dto.getDepartamento());
		departamento.getFuncionario().add(entity);
		departamentoRepository.save(departamento);
		entity.setDepartamento(departamento);
		entity = repository.save(entity);
		}catch(InvalidDataAccessApiUsageException e) {
			throw new  ResourceNotFoundException("Funcionário não pode ser inserido ser Departamento!");
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Departamento não existe: " + dto.getDepartamento());
		}
	}
}
