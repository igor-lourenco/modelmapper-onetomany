package com.modelmapper.crud.dto;

import java.io.Serializable;

import com.modelmapper.crud.entities.Funcionario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	public FuncionarioDTO(Funcionario obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}
}
