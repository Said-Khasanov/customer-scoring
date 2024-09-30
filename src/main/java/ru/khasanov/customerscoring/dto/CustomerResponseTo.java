package ru.khasanov.customerscoring.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerResponseTo {
    private Long id;
    private String inn;
    private Integer region;
    private BigDecimal capital;
}