package br.com.testevv.simuladorselic;

import java.math.BigDecimal;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxaSelic {
	private BigDecimal valor;
}
