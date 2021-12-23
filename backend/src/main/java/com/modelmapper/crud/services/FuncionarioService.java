package com.modelmapper.crud.services;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.modelmapper.crud.dto.FuncionarioDTO;
import com.modelmapper.crud.entities.Departamento;
import com.modelmapper.crud.entities.Funcionario;
import com.modelmapper.crud.repositories.DepartamentoRepository;
import com.modelmapper.crud.repositories.FuncionarioRepository;
import com.modelmapper.crud.services.exceptions.ResourceNotFoundException;

@Service
public class FuncionarioService  implements GenericService<Funcionario, FuncionarioDTO, Long>{

	@Autowired
	private FuncionarioRepository repository;
	@Autowired
	private DepartamentoRepository departamentoRepository;
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public JpaRepository<Funcionario, Long> repository() {
		return repository;
	}

	@Override
	public FuncionarioDTO insert(FuncionarioDTO dto) {
		Funcionario entity = modelMapper.map(dto, Funcionario.class);
		copiaEntidade(entity, dto);
		return modelMapper.map(entity, FuncionarioDTO.class);
	}

	@Override
	public FuncionarioDTO update(Long id, FuncionarioDTO dto) {
		Funcionario entity = repository.getById(id);
		 modelMapper.map(dto, entity);
		copiaEntidade(entity, dto);
		return  modelMapper.map(entity, FuncionarioDTO.class);
	}


	private void copiaEntidade(Funcionario entity, FuncionarioDTO dto) {
		try {
			Departamento departamento = departamentoRepository.getById(dto.getDepartamento());
			departamento.getFuncionario().add(entity);
			departamentoRepository.save(departamento);
			entity.setDepartamento(departamento);
			entity = repository.save(entity);
		} catch (InvalidDataAccessApiUsageException e) {
			throw new ResourceNotFoundException("Funcionário não pode ser inserido ser Departamento!");
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Departamento não existe: " + dto.getDepartamento());
		}
	}
}
