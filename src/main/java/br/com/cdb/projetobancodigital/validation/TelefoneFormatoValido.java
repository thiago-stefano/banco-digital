package br.com.cdb.projetobancodigital.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TelefoneFormatoValidoValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TelefoneFormatoValido {
	String message() default "Telefone inválido. Use o formato (xx)xxxxx-xxxx";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
