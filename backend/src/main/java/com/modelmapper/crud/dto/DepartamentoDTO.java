package com.modelmapper.crud.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.modelmapper.crud.entities.Departamento;
import com.modelmapper.crud.entities.Funcionario;

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
	private Set<FuncionarioDTO> funcionario = new HashSet<>();
	
	public DepartamentoDTO(Departamento obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}
	public DepartamentoDTO(Departamento obj, List<Funcionario> funcionario) {
		this(obj);
		funcionario.forEach(f -> this.funcionario.add(new FuncionarioDTO(f)));
	}
	
	
	@Override // pra mapear o id na API quando usar PUT
	public String toString() {
		return id.toString();
	}
}
