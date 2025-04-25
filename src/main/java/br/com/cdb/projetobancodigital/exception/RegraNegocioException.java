package br.com.cdb.projetobancodigital.exception;

public class RegraNegocioException extends RuntimeException {
    
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}