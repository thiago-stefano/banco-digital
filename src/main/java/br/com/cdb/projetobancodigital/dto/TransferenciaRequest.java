package br.com.cdb.projetobancodigital.dto;

import java.math.BigDecimal;

import br.com.cdb.projetobancodigital.enums.TipoConta;

public class TransferenciaRequest {

    private String numeroConta;
    private TipoConta origem;
    private TipoConta destino;
    private BigDecimal valor;

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public TipoConta getOrigem() {
        return origem;
    }

    public void setOrigem(TipoConta origem) {
        this.origem = origem;
    }

    public TipoConta getDestino() {
        return destino;
    }

    public void setDestino(TipoConta destino) {
        this.destino = destino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
