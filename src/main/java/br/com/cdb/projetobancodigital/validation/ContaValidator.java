package br.com.cdb.projetobancodigital.validation;

import java.math.BigDecimal;

public class ContaValidator {

    public static void validarSaldoInicial(BigDecimal saldoInicial) {
        if (saldoInicial != null && saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Saldo inicial não pode ser negativo");
        }
    }

    public static void validarTipoConta(Object tipoConta) {
        if (tipoConta == null) {
            throw new RuntimeException("Tipo da conta é obrigatório");
        }
    }

    public static void validarValorPositivo(BigDecimal valor, String operacao) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor para " + operacao + " deve ser maior que zero");
        }
    }

    public static void validarSaldoSuficiente(BigDecimal saldo, BigDecimal valor) {
        if (saldo.compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
    }

    public static void validarContasDiferentes(String contaOrigem, String contaDestino) {
        if (contaOrigem.equals(contaDestino)) {
            throw new RuntimeException("Não é possível transferir para a mesma conta");
        }
    }

    public static void validarTransferenciaEntreTipos(Object tipoOrigem, Object tipoDestino) {
        if (tipoOrigem == null || tipoDestino == null) {
            throw new RuntimeException("Tipo de conta de origem e destino devem ser informados");
        }
        if (tipoOrigem.equals(tipoDestino)) {
            throw new RuntimeException("Tipo de conta de origem e destino devem ser diferentes");
        }
    }
}
