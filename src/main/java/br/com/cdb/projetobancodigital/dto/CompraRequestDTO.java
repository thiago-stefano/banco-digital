package br.com.cdb.projetobancodigital.dto;

import br.com.cdb.projetobancodigital.enums.TipoCartao;

public class CompraRequestDTO {

    private String numeroCartao;
    private double valor;
    private String descricao;
    private TipoCartao tipo;

    // Getters e Setters
    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoCartao getTipo() {
        return tipo;
    }

    public void setTipo(TipoCartao tipo) {
        this.tipo = tipo;
    }
}
