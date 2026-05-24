package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class PlanoValidationValidator implements ConstraintValidator<PlanoValidation, String> {

    // Lista de categorias/nomes de planos permitidos no sistema
    private static final Set<String> PLANOS_PERMITIDOS = Set.of("BÁSICO", "BASICO", "PADRÃO", "PADRAO", "PREMIUM");

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        if (valor == null) {
            return true; // Deixa o @NotBlank/@NotNull lidar com a obrigatoriedade
        }
        return PLANOS_PERMITIDOS.contains(valor.toUpperCase().trim());
    }
}