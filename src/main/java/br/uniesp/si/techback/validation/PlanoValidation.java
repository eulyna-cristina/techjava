package br.uniesp.si.techback.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = PlanoValidationValidator.class)
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PlanoValidation {
    String message() default "Nome do plano inválido. Use apenas: BÁSICO, PADRÃO ou PREMIUM.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}