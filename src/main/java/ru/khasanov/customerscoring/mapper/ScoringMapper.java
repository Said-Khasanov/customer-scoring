package ru.khasanov.customerscoring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.khasanov.customerscoring.dto.ScoringResponseTo;
import ru.khasanov.customerscoring.entity.Scoring;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ScoringMapper {
    @Mapping(target = "customerId", source = "customer.id")
    ScoringResponseTo toResponseTo(Scoring scoring);
}
