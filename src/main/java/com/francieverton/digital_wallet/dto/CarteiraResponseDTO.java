package com.francieverton.digital_wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CarteiraResponseDTO {

    private Long id;
    private BigDecimal saldo;
    private String moeda;
}
