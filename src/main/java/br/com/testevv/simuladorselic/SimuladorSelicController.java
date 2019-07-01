package br.com.testevv.simuladorselic;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simulador/selic")
public class SimuladorSelicController {

	@Autowired
	private CalculadoraParcelas calculadoraParcelas;

	@PostMapping("/parcelas")
	public ResponseEntity<Collection<Parcela>> simular(
			@RequestBody @NotNull(message = "Favor informar o produto e a condição de pagamento.") EntradaSimulador entradaSimulador) {
		List<Parcela> parcelas = calculadoraParcelas.calcularParcelas(entradaSimulador.getProduto(),
				entradaSimulador.getCondicaoPagamento());
		return ResponseEntity.ok(parcelas);
	}
}
