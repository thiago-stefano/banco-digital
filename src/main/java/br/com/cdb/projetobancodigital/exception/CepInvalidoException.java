package br.com.cdb.projetobancodigital.exception;

public class CepInvalidoException extends RuntimeException {
    public CepInvalidoException(String cep) {
        super("CEP " + cep + " não encontrado. Verifique se digitou corretamente.");
    }
}