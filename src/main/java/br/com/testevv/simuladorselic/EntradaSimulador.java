package br.com.testevv.simuladorselic;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EntradaSimulador {

	@NotNull(message = "Favor informar o produto.")
	private Produto produto;

	@NotNull(message = "Favor informar a condição de pagamento.")
	private CondicaoPagamento condicaoPagamento;
}
