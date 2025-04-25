package br.com.cdb.projetobancodigital.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferenciaDTO {

    @NotBlank(message = "O número da conta de origem é obrigatório.")
    private String numeroContaOrigem;

    @NotBlank(message = "O número da conta de destino é obrigatório.")
    private String numeroContaDestino;

    @NotNull(message = "O valor da transferência é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    public String getNumeroContaOrigem() {
        return numeroContaOrigem;
    }

    public void setNumeroContaOrigem(String numeroContaOrigem) {
        this.numeroContaOrigem = numeroContaOrigem;
    }

    public String getNumeroContaDestino() {
        return numeroContaDestino;
    }

    public void setNumeroContaDestino(String numeroContaDestino) {
        this.numeroContaDestino = numeroContaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
