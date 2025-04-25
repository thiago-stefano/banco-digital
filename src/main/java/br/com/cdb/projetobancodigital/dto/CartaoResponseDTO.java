package br.com.cdb.projetobancodigital.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.cdb.projetobancodigital.enums.TipoCartao;

public class CartaoResponseDTO {

    private Long id;
    private String numero;
    private String nomeTitular;
    private LocalDate validade;
    private boolean ativo;
    private BigDecimal limite;
    private BigDecimal limiteDiario;
    private TipoCartao tipo;
    private String numeroConta;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNomeTitular() {
		return nomeTitular;
	}
	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}
	public LocalDate getValidade() {
		return validade;
	}
	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public BigDecimal getLimite() {
		return limite;
	}
	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}
	public BigDecimal getLimiteDiario() {
		return limiteDiario;
	}
	public void setLimiteDiario(BigDecimal limiteDiario) {
		this.limiteDiario = limiteDiario;
	}
	public TipoCartao getTipo() {
		return tipo;
	}
	public void setTipo(TipoCartao tipo) {
		this.tipo = tipo;
	}
	public String getNumeroConta() {
		return numeroConta;
	}
	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

}