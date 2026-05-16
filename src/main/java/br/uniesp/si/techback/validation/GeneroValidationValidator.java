package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GeneroValidationValidator implements ConstraintValidator<GeneroValidation, String> {

    // Lista de gêneros válidos para o IESPFLIX
    private final List<String> generosValidos = Arrays.asList(
            "Terror", "Ação", "Comédia", "Drama", "Romance", "Ficção Científica", "Suspense"
    );

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        // Se o valor for nulo, a validação passa (deixamos o @NotNull ou @NotBlank cuidar disso se precisar)
        if (Objects.isNull(valor)) {
            return true;
        }

        // Verifica se o gênero enviado está na nossa lista, ignorando maiúsculas/minúsculas
        return generosValidos.stream()
                .anyMatch(genero -> genero.equalsIgnoreCase(valor));
    }
}