package br.uniesp.si.techback.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = GeneroValidationValidator.class) // Corrigido para 'validatedBy'
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneroValidation {

    String message() default "O gênero não está na lista disponível";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {}; // Corrigido para 'payload' minúsculo
}
