package br.com.testevv.simuladorselic.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CondicaoPagamento {

	@NotNull(message = "Favor informar o valor de entrada.")
	private BigDecimal valorEntrada;

	@NotNull(message = "Favor informar a quantidade de parcelas.")
	private Integer qtdeParcelas;
}
