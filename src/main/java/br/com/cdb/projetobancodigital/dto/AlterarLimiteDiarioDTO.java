package br.com.cdb.projetobancodigital.dto;

import java.math.BigDecimal;

public class AlterarLimiteDiarioDTO {
    private Long id;
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
	private BigDecimal novoLimite;
}

