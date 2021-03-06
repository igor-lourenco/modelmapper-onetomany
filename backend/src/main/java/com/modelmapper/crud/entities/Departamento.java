package com.modelmapper.crud.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.modelmapper.crud.dto.DepartamentoDTO;
import com.modelmapper.crud.util.ConverterEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_departamento")
public class Departamento implements ConverterEntity<DepartamentoDTO>, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@OneToMany(mappedBy = "departamento")
	private List<Funcionario> funcionario = new ArrayList<>();

	@Override
	public DepartamentoDTO converterEntity() {
		return new DepartamentoDTO(this, funcionario);
	}
}
