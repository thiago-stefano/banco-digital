package br.com.cdb.projetobancodigital.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;


public class PixDTO {

    @NotBlank(message = "Número da conta de origem é obrigatório.")
    private String numeroContaOrigem;

    @NotBlank(message = "Chave Pix de destino é obrigatória.")
    private String chavePixDestino;

    @NotNull(message = "Valor é obrigatório.")
    @DecimalMin(value = "0.01", message = "Valor mínimo para transferência é 0.01.")
    private BigDecimal valor;

	public String getNumeroContaOrigem() {
		return numeroContaOrigem;
	}

	public void setNumeroContaOrigem(String numeroContaOrigem) {
		this.numeroContaOrigem = numeroContaOrigem;
	}

	public String getChavePixDestino() {
		return chavePixDestino;
	}

	public void setChavePixDestino(String chavePixDestino) {
		this.chavePixDestino = chavePixDestino;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
