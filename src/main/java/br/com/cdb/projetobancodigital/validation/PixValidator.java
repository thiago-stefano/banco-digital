package br.com.cdb.projetobancodigital.validation;

import br.com.cdb.projetobancodigital.util.CpfUtils;

public class PixValidator {

    public static boolean chavePixValida(String chave) {
        if (chave == null) return false;

        String regexTelefone = "^\\+55\\d{11}$";
        String regexEmail = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$";
        String regexUUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";

        if (chave.matches("^\\d{11}$")) {
            return CpfUtils.isValid(chave);
        }

        return chave.matches(regexTelefone)
            || chave.matches(regexEmail)
            || chave.matches(regexUUID);
    }

    public static void validarOuLancarExcecao(String chave) {
        if (!chavePixValida(chave)) {
            throw new RuntimeException("Chave Pix inválida. Informe um CPF, telefone, e-mail ou chave aleatória válidos.");
        }
    }
}

