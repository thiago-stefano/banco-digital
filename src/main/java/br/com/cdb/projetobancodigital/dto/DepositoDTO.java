package br.com.cdb.projetobancodigital.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class DepositoDTO {

    @NotBlank(message = "O número da conta é obrigatório.")
    private String numeroConta;

    public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor mínimo para depósito é R$0,01.")
    private BigDecimal valor;
}
