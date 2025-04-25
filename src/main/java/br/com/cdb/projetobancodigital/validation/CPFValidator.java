package br.com.cdb.projetobancodigital.validation;

import br.com.cdb.projetobancodigital.util.CpfUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPFValido, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        return CpfUtils.isValid(cpf);
    }
}
