package br.com.cdb.projetobancodigital.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PixRequestDTO {
    @NotBlank
    private String numeroContaOrigem;

    @NotBlank
    private String chavePix;

    @NotNull
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero.")
    private BigDecimal valor;

	public String getNumeroContaOrigem() {
		return numeroContaOrigem;
	}

	public void setNumeroContaOrigem(String numeroContaOrigem) {
		this.numeroContaOrigem = numeroContaOrigem;
	}

	public String getChavePix() {
		return chavePix;
	}

	public void setChavePix(String chavePix) {
		this.chavePix = chavePix;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


}
