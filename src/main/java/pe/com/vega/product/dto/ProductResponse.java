package pe.com.vega.product.dto;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price) {}
