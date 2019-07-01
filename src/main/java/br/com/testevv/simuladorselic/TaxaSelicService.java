package br.com.testevv.simuladorselic;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TaxaSelicService {

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${bcb.service.selic}")
	private String bcbServiceSelic;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public TaxaSelicService getTaxaSelicService() {
		return new TaxaSelicService();
	}

	public TaxaSelic getTaxaSelic() {
		Optional<TaxaSelic> taxaSelic = Optional.empty();
		ParameterizedTypeReference<Collection<TaxaSelic>> taxasSelicTypeReference = new ParameterizedTypeReference<Collection<TaxaSelic>>() {
		};
		ResponseEntity<Collection<TaxaSelic>> responseEntity = restTemplate.exchange(bcbServiceSelic, HttpMethod.GET,
				null, taxasSelicTypeReference);
		if (responseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
			Collection<TaxaSelic> taxasSelic = responseEntity.getBody();
			if (taxasSelic != null) {
				taxaSelic = taxasSelic.stream().findFirst();
			}
		} else {
			throw new TaxaSelicFailException();
		}
		return taxaSelic.orElseThrow(TaxaSelicNotFoundException::new);
	}
}
