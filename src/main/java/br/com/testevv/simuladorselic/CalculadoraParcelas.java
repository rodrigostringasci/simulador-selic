package br.com.testevv.simuladorselic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import br.com.testevv.simuladorselic.model.CondicaoPagamento;
import br.com.testevv.simuladorselic.model.Parcela;
import br.com.testevv.simuladorselic.model.Produto;

@Configuration
public class CalculadoraParcelas {

	@Autowired
	private TaxaSelicService taxaSelicService;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public CalculadoraParcelas getCalculadoraParcelas() {
		return new CalculadoraParcelas();
	}

	public List<Parcela> calcularParcelas(Produto produto, CondicaoPagamento condicaoPagamento) {
		BigDecimal valorParcela = null;
		BigDecimal taxaJuros = BigDecimal.ZERO;
		MathContext mathContext = MathContext.UNLIMITED;

		if (condicaoPagamento.getQtdeParcelas() > 6) {
			taxaJuros = taxaSelicService.getTaxaSelic().getValor();
			valorParcela = produto
					.getValor()
					.subtract(condicaoPagamento.getValorEntrada(), mathContext)
					.multiply(
							BigDecimal.ONE.add(taxaJuros.multiply(
									BigDecimal.valueOf(condicaoPagamento.getQtdeParcelas()), mathContext), mathContext),
							mathContext).divide(BigDecimal.valueOf(condicaoPagamento.getQtdeParcelas()), mathContext);
		} else {
			valorParcela = produto.getValor().subtract(condicaoPagamento.getValorEntrada(), mathContext)
					.divide(BigDecimal.valueOf(condicaoPagamento.getQtdeParcelas()), mathContext);
		}

		valorParcela = valorParcela.setScale(2, RoundingMode.HALF_UP);
		List<Parcela> parcelas = new ArrayList<>(condicaoPagamento.getQtdeParcelas());
		for (int i = 0; i < condicaoPagamento.getQtdeParcelas(); i++) {
			Parcela parcela = new Parcela();
			parcela.setNumeroParcela(i + 1);
			parcela.setTaxaJurosAoMes(taxaJuros);
			parcela.setValor(valorParcela);

			parcelas.add(parcela);
		}
		return parcelas;
	}

}
