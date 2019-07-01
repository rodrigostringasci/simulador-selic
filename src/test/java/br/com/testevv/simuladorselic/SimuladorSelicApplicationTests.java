package br.com.testevv.simuladorselic;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class SimuladorSelicApplicationTests {

	@LocalServerPort
	private String port;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testParcelasComJuros() {
		EntradaSimulador request = new EntradaSimulador();
		request.setProduto(new Produto());
		request.getProduto().setCodigo(123l);
		request.getProduto().setNome("Produto teste");
		request.getProduto().setValor(BigDecimal.valueOf(99.99d));
		request.setCondicaoPagamento(new CondicaoPagamento());
		request.getCondicaoPagamento().setQtdeParcelas(10);
		request.getCondicaoPagamento().setValorEntrada(BigDecimal.valueOf(9.99d));

		ParameterizedTypeReference<Collection<Parcela>> parcelasTypeReference = new ParameterizedTypeReference<Collection<Parcela>>() {
		};
		ResponseEntity<Collection<Parcela>> response = this.restTemplate.exchange(
				String.format("http://localhost:%s/simulador/selic/parcelas", port), HttpMethod.POST,
				new HttpEntity<EntradaSimulador>(request), parcelasTypeReference);

		assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().size()).isEqualTo(10);
		assertThat(response.getBody().stream().findFirst().get().getTaxaJurosAoMes()).isGreaterThan(BigDecimal.ZERO);
	}

	@Test
	public void testParcelasSemJuros() {
		EntradaSimulador request = new EntradaSimulador();
		request.setProduto(new Produto());
		request.getProduto().setCodigo(123l);
		request.getProduto().setNome("Produto teste");
		request.getProduto().setValor(BigDecimal.valueOf(99.99d));
		request.setCondicaoPagamento(new CondicaoPagamento());
		request.getCondicaoPagamento().setQtdeParcelas(6);
		request.getCondicaoPagamento().setValorEntrada(BigDecimal.valueOf(9.99d));

		ParameterizedTypeReference<Collection<Parcela>> parcelasTypeReference = new ParameterizedTypeReference<Collection<Parcela>>() {
		};
		ResponseEntity<Collection<Parcela>> response = this.restTemplate.exchange(
				String.format("http://localhost:%s/simulador/selic/parcelas", port), HttpMethod.POST,
				new HttpEntity<EntradaSimulador>(request), parcelasTypeReference);

		assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().size()).isEqualTo(6);
		assertThat(response.getBody().stream().findFirst().get().getTaxaJurosAoMes()).isEqualTo(BigDecimal.ZERO);
	}
}
