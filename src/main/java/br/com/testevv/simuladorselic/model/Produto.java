package br.com.testevv.simuladorselic.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Produto {

	@NotNull(message = "Favor informar o código do produto.")
	private Long codigo;

	private String nome;

	@NotNull(message = "Favor informar o valor do produto.")
	private BigDecimal valor;
}
