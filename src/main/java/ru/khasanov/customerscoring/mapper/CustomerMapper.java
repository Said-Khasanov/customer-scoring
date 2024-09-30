package ru.khasanov.customerscoring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.khasanov.customerscoring.dto.CustomerRequestTo;
import ru.khasanov.customerscoring.dto.CustomerResponseTo;
import ru.khasanov.customerscoring.entity.Customer;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {
    Customer toEntity(CustomerRequestTo customerRequestTo);
    Customer toEntity(CustomerResponseTo customerResponseTo);
    CustomerResponseTo toResponseTo(Customer customer);
}
