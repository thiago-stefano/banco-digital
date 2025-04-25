package br.com.cdb.projetobancodigital.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class SaldoResponseDTO {
    private String numeroConta;
    
    public SaldoResponseDTO(String numeroConta, BigDecimal saldo){
    	this.numeroConta = numeroConta;
    	this.saldo = saldo;
    }
    public String getNumeroConta() {
		return numeroConta;
	}
	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	private BigDecimal saldo;
}