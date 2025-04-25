package br.com.cdb.projetobancodigital.dto;

public class AlterarStatusDTO {
    private Long id;
    private boolean ativo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
