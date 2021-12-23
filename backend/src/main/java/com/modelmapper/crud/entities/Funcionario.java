package com.modelmapper.crud.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.modelmapper.crud.dto.FuncionarioDTO;
import com.modelmapper.crud.util.ConverterEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_funcionario")
public class Funcionario implements ConverterEntity<FuncionarioDTO>, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;

	@ManyToOne
	@JoinColumn(name = "departamento_id")
	private Departamento departamento;

	@Override
	public FuncionarioDTO converterEntity() {
		return new FuncionarioDTO(this);
	}
}
