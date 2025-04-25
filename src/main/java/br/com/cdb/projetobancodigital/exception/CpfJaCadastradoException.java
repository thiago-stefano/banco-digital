package br.com.cdb.projetobancodigital.exception;

public class CpfJaCadastradoException extends RuntimeException {
    public CpfJaCadastradoException(String cpf) {
        super("Já existe um cliente cadastrado com o CPF " + cpf);
    }
}