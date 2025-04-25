package br.com.cdb.projetobancodigital.dto;

import br.com.cdb.projetobancodigital.enums.TipoTransacao;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoResponseDTO {

    private TipoTransacao tipo;
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private String descricao;
    private String numeroConta;

    public TransacaoResponseDTO(TipoTransacao tipo, BigDecimal valor, LocalDateTime dataHora, String descricao, String numeroConta) {
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.numeroConta = numeroConta;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }
}
