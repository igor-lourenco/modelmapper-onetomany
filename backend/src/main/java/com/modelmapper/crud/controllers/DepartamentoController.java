package com.modelmapper.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modelmapper.crud.dto.DepartamentoDTO;
import com.modelmapper.crud.entities.Departamento;
import com.modelmapper.crud.services.DepartamentoService;
import com.modelmapper.crud.services.GenericService;

@RestController
@RequestMapping(value = "/departamentos")
public class DepartamentoController implements GenericController<Departamento, DepartamentoDTO, Long> {
	
	@Autowired
	private DepartamentoService service;
	

	@Override
	public GenericService<Departamento, DepartamentoDTO, Long> service() {
		return service;
	}
}
