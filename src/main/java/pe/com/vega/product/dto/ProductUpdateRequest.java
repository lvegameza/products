package pe.com.vega.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductUpdateRequest(@NotBlank String name,
                                   @Positive BigDecimal price) {
}
