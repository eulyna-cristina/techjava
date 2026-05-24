package br.uniesp.si.techback.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE}) // Define que a anotação será usada na classe DTO inteira
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PeriodoValidator.class)
@Documented
public @interface ValidPeriodo {
    String message() default "A data de início deve ser anterior à data de término da assinatura.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}