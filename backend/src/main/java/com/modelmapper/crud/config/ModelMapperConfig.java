package com.modelmapper.crud.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.modelmapper.crud.dto.DepartamentoDTO;
import com.modelmapper.crud.dto.FuncionarioDTO;
import com.modelmapper.crud.entities.Departamento;
import com.modelmapper.crud.entities.Funcionario;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		// mapeando id da Pessoa com pessoaID do Carro
		mapper.createTypeMap(Funcionario.class, FuncionarioDTO.class)
		.addMapping(p -> p.getId(),	(dest, valor) -> dest.setDepartamento((Long) valor))
		.addMapping(p -> p.getDepartamento().getId(),	(dest, valor) -> dest.setDepartamento((Long)valor));
		
		// método para fazer update no service e associar dto com a entidade
		mapper.addMappings(new PropertyMap<FuncionarioDTO, Funcionario>() {
			@Override
			protected void configure() {
				skip(destination.getId());
			}
		});
		// método para fazer update no service e associar dto com a entidade
		mapper.addMappings(new PropertyMap<DepartamentoDTO, Departamento>() {
			@Override
			protected void configure() {
				skip(destination.getId());
			}
		});
		return mapper;
	}
}
