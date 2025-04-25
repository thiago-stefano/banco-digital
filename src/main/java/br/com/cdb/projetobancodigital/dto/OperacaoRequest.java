package br.com.cdb.projetobancodigital.dto;

import java.math.BigDecimal;

public class OperacaoRequest {
    private BigDecimal valor;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
