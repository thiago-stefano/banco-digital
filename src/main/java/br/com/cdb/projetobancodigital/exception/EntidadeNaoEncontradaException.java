package br.com.cdb.projetobancodigital.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

    private final String codigo;

    public EntidadeNaoEncontradaException(String mensagem, String codigo) {
        super(mensagem);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
