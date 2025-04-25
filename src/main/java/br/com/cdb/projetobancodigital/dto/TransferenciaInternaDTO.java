package br.com.cdb.projetobancodigital.dto;

import java.math.BigDecimal;

import br.com.cdb.projetobancodigital.enums.TipoConta;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class TransferenciaInternaDTO {

    @NotNull(message = "O ID do cliente é obrigatório.")
    private Long clienteId;

    public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public TipoConta getOrigem() {
		return origem;
	}

	public void setOrigem(TipoConta origem) {
		this.origem = origem;
	}

	public TipoConta getDestino() {
		return destino;
	}

	public void setDestino(TipoConta destino) {
		this.destino = destino;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@NotNull(message = "O tipo da conta de origem é obrigatório.")
    private TipoConta origem;

    @NotNull(message = "O tipo da conta de destino é obrigatório.")
    private TipoConta destino;

    @NotNull(message = "O valor da transferência é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor mínimo é R$0,01.")
    private BigDecimal valor;
}
