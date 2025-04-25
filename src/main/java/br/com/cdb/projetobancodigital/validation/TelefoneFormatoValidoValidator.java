package br.com.cdb.projetobancodigital.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneFormatoValidoValidator implements ConstraintValidator<TelefoneFormatoValido, String> {

	private static final String TELEFONE_REGEX = "\\(\\d{2}\\)\\d{5}-\\d{4}";

	@Override
	public boolean isValid(String telefone, ConstraintValidatorContext context) {
		return telefone != null && telefone.matches(TELEFONE_REGEX);
	}
}
