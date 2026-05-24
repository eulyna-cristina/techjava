package br.uniesp.si.techback.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TipoPagamentoValidator.class)
@Documented
public @interface ValidTipoPagamento {
    String message() default "Tipo de pagamento inválido. Use: CARTAO_CREDITO, DEBITO ou PIX.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}