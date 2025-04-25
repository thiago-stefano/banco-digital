package br.com.cdb.projetobancodigital.dto;

import java.math.BigDecimal;

import br.com.cdb.projetobancodigital.enums.TipoCartao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class PagamentoCartaoDTO {
	@NotNull
    private Long id;
    private String senha;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valor;
    private String descricao;
    private TipoCartao tipo;
    public TipoCartao getTipo() {
		return tipo;
	}
	public void setTipo(TipoCartao tipo) {
		this.tipo = tipo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
