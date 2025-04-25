package br.com.cdb.projetobancodigital.dto;

import br.com.cdb.projetobancodigital.enums.TipoConta;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ContaRequestDTO {

    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "O tipo da conta é obrigatório")
    private TipoConta tipoConta;

    @NotNull(message = "O saldo inicial é obrigatório")
    @Min(value = 0, message = "O saldo inicial não pode ser negativo")
    private BigDecimal saldoInicial;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
}
