package ru.khasanov.customerscoring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khasanov.customerscoring.dto.CustomerRequestTo;
import ru.khasanov.customerscoring.dto.CustomerResponseTo;
import ru.khasanov.customerscoring.entity.Customer;
import ru.khasanov.customerscoring.mapper.CustomerMapper;
import ru.khasanov.customerscoring.repository.CustomerRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerResponseTo> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponseTo)
                .toList();
    }

    public CustomerResponseTo findById(Long id) {
        return customerMapper.toResponseTo(getExistingCustomer(id));
    }

    public CustomerResponseTo save(CustomerRequestTo customerRequestTo) {
        Customer customer = customerMapper.toEntity(customerRequestTo);
        customer = customerRepository.save(customer);
        return customerMapper.toResponseTo(customer);
    }

    public CustomerResponseTo update(Long id, CustomerRequestTo customerRequestTo) {
        Customer customer = getExistingCustomer(id);
        customer.setCapital(Objects.requireNonNullElse(customerRequestTo.getCapital(), customer.getCapital()));
        customer.setInn(Objects.requireNonNullElse(customerRequestTo.getInn(), customer.getInn()));
        customer.setRegion(Objects.requireNonNullElse(customerRequestTo.getRegion(), customer.getRegion()));
        customer = customerRepository.save(customer);
        return customerMapper.toResponseTo(customer);
    }

    public void delete(Long id) {
        getExistingCustomer(id);
        customerRepository.deleteById(id);
    }

    private Customer getExistingCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
