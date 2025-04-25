package br.com.cdb.projetobancodigital.dto;

import java.math.BigDecimal;

public class AlterarLimiteDTO {
    private Long id;
    private BigDecimal novoLimite;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getNovoLimite() {
		return novoLimite;
	}
	public void setNovoLimite(BigDecimal novoLimite) {
		this.novoLimite = novoLimite;
	}

}
