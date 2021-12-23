package com.modelmapper.crud.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.modelmapper.crud.services.GenericService;
import com.modelmapper.crud.util.ConverterEntity;

public interface GenericController<T extends ConverterEntity<DTO>, DTO, ID> {
	
	GenericService<T, DTO, ID> service();
	
	@PutMapping(value = "/{id}")
	default ResponseEntity<DTO> update(@PathVariable ID id, @RequestBody DTO dto){
		var obj = service().update(id, dto);
		return ResponseEntity.ok(obj);
	}

	
	@GetMapping(value = "/{id}")
	default ResponseEntity<DTO> findById(@PathVariable ID id){
		var obj = service().findById(id);
		return ResponseEntity.ok(obj);
	}
	
	@PostMapping
	default ResponseEntity<DTO> insert(@RequestBody DTO dto){
		var obj = service().insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(obj.toString()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@GetMapping
	default ResponseEntity<List<DTO>> findAll(){
		var obj = service().findAll();
		return ResponseEntity.ok(obj);
	}
	@DeleteMapping(value = "/{id}")
	default ResponseEntity<DTO> delete(@PathVariable ID id){
		service().delete(id);
		return ResponseEntity.noContent().build();
	}
}
