package br.com.cdb.projetobancodigital.dto;

import jakarta.validation.constraints.NotBlank;


public class PixCadastroDTO {

    @NotBlank(message = "Número da conta é obrigatório.")
    private String numeroConta;

    @NotBlank(message = "Chave Pix é obrigatória.")
    private String chavePix;

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getChavePix() {
		return chavePix;
	}

	public void setChavePix(String chavePix) {
		this.chavePix = chavePix;
	}
}

