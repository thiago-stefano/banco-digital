package br.com.cdb.projetobancodigital.entity;

import br.com.cdb.projetobancodigital.enums.TipoCartao;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    private String nomeTitular;

    private LocalDate validade;

    private String senha;

    private boolean ativo;

    private BigDecimal limite;

    private BigDecimal limiteDiario;

    @Enumerated(EnumType.STRING)
    private TipoCartao tipo;

    @ManyToOne
    private Conta conta;

    private BigDecimal faturaAtual;

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public BigDecimal getFatura() {
		return faturaAtual;
	}

	public void setFatura(BigDecimal faturaAtual) {
		this.faturaAtual = faturaAtual;
	}

}
