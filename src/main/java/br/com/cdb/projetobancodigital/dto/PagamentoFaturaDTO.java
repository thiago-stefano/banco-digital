package br.com.cdb.projetobancodigital.dto;

import java.math.BigDecimal;

public class PagamentoFaturaDTO {
    private Long id;
    private BigDecimal valor;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}