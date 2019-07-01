package br.com.testevv.simuladorselic;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Parcela {
	private Integer numeroParcela;
	private BigDecimal valor;
	private BigDecimal taxaJurosAoMes;
}
