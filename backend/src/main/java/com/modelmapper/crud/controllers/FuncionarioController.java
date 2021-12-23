package com.modelmapper.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modelmapper.crud.dto.FuncionarioDTO;
import com.modelmapper.crud.entities.Funcionario;
import com.modelmapper.crud.services.FuncionarioService;
import com.modelmapper.crud.services.GenericService;

@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionarioController implements GenericController<Funcionario, FuncionarioDTO, Long> {
	
	@Autowired
	private FuncionarioService service;

	@Override
	public GenericService<Funcionario, FuncionarioDTO, Long> service() {
		return service;
	}

}
