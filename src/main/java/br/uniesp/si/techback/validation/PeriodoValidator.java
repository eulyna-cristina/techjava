package br.uniesp.si.techback.validation;

import br.uniesp.si.techback.dto.AssinaturaDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeriodoValidator implements ConstraintValidator<ValidPeriodo, AssinaturaDTO> {

    @Override
    public boolean isValid(AssinaturaDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getDataInicio() == null || dto.getDataFim() == null) {
            return true; // Deixa o @NotNull cuidar de campos vazios isolados
        }

        // A data de início deve ser estritamente antes (ou igual) à data de fim
        return !dto.getDataInicio().isAfter(dto.getDataFim());
    }
}