package br.com.cdb.projetobancodigital.dto;

import br.com.cdb.projetobancodigital.enums.TipoConta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ComprovanteTransferenciaDTO {
    private String mensagem;
    private Long clienteId;
    private TipoConta tipoContaOrigem;
    private TipoConta tipoContaDestino;
    private String numeroContaOrigem;
    private String numeroContaDestino;
    public String getNumeroContaOrigem() {
		return numeroContaOrigem;
	}

	public void setNumeroContaOrigem(String numeroContaOrigem) {
		this.numeroContaOrigem = numeroContaOrigem;
	}

	public String getNumeroContaDestino() {
		return numeroContaDestino;
	}

	public void setNumeroContaDestino(String numeroContaDestino) {
		this.numeroContaDestino = numeroContaDestino;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public TipoConta getTipoContaOrigem() {
		return tipoContaOrigem;
	}

	public void setTipoContaOrigem(TipoConta tipoContaOrigem) {
		this.tipoContaOrigem = tipoContaOrigem;
	}

	public TipoConta getTipoContaDestino() {
		return tipoContaDestino;
	}

	public void setTipoContaDestino(TipoConta tipoContaDestino) {
		this.tipoContaDestino = tipoContaDestino;
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

	private BigDecimal valor;
    private LocalDateTime dataHora;

    public ComprovanteTransferenciaDTO(String mensagem, Long clienteId, TipoConta origem, TipoConta destino, BigDecimal valor, String numeroContaOrigem, String numeroContaDestino) {
        this.mensagem = mensagem;
        this.clienteId = clienteId;
        this.tipoContaOrigem = origem;
        this.tipoContaDestino = destino;
        this.valor = valor;
        this.numeroContaOrigem = numeroContaOrigem;
        this.numeroContaDestino = numeroContaDestino;
        this.dataHora = LocalDateTime.now();
    }

}
