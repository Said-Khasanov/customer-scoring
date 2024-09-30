package ru.khasanov.customerscoring.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ScoringResponseTo {
    private Long customerId;
    private boolean result;
    private String innResult;
    private String regionResult;
    private String capitalResult;
    private String residentResult;
}
