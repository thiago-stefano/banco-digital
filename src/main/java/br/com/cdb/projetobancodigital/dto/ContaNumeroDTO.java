package br.com.cdb.projetobancodigital.dto;

public class ContaNumeroDTO {
    private String numeroConta;

    public ContaNumeroDTO() {
    }

    public ContaNumeroDTO(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }
}
