package com.modelmapper.crud.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.modelmapper.crud.services.exceptions.DatabaseException;
import com.modelmapper.crud.services.exceptions.ResourceNotFoundException;
import com.modelmapper.crud.util.ConverterEntity;

@Service
public interface GenericService <T extends ConverterEntity<DTO>, DTO, ID > {
	
	JpaRepository<T, ID> repository();
	
	@Transactional
	DTO insert(DTO dto);

	@Transactional
	DTO update(ID id, DTO dto);
	
	@Transactional(readOnly = true)
	default List<DTO> findAll() {
		List<T> obj = repository().findAll(Sort.by("nome"));
		return obj.stream().map(u -> u.converterEntity()).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	default DTO findById(ID id) {
		T obj = repository().findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("id não existe: " + id));
		 return obj.converterEntity();
	}

	default void delete(ID id) {
		try {
			repository().deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("id não existe: " + id);

		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Exceção no banco");
		}
	}

}
