package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class TipoPagamentoValidator implements ConstraintValidator<ValidTipoPagamento, String> {

    private static final Set<String> TIPOS_VALIDOS = Set.of("CARTAO_CREDITO", "DEBITO", "PIX");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return TIPOS_VALIDOS.contains(value.toUpperCase());
    }
}