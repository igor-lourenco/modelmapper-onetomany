package com.modelmapper.crud.dto;

import java.io.Serializable;

import com.modelmapper.crud.entities.Departamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	
	public DepartamentoDTO(Departamento obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}
}
