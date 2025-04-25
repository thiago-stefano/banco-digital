package br.com.cdb.projetobancodigital.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ComprovanteTransacaoDTO {
    private String numeroConta;
    private BigDecimal valor;
    private String tipoTransacao;
    private LocalDateTime dataHora;
    private String descricao;
    
    public ComprovanteTransacaoDTO(String numeroConta, BigDecimal valor, String tipoTransacao, LocalDateTime dataHora, String descricao) {
    	this.numeroConta = numeroConta;
    	this.valor = valor;
    	this.tipoTransacao = tipoTransacao;
    	this.dataHora = dataHora;
    	this.descricao = descricao;
    }

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

	public String getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(String tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
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
}