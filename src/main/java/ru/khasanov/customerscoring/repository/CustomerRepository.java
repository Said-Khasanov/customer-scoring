package ru.khasanov.customerscoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khasanov.customerscoring.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
