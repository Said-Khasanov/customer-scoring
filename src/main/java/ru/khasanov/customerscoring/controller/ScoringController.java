package ru.khasanov.customerscoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khasanov.customerscoring.dto.CustomerRequestTo;
import ru.khasanov.customerscoring.dto.ScoringResponseTo;
import ru.khasanov.customerscoring.entity.Customer;
import ru.khasanov.customerscoring.mapper.CustomerMapper;
import ru.khasanov.customerscoring.service.CustomerService;
import ru.khasanov.customerscoring.service.ScoringService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/scoring")
public class ScoringController {
    private final ScoringService scoringService;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping
    public ResponseEntity<List<ScoringResponseTo>> findAll() {
        return ResponseEntity.ok(scoringService.findAll());
    }

    @PostMapping
    public ResponseEntity<ScoringResponseTo> evaluate(@RequestBody CustomerRequestTo customerRequestTo) {
        Customer customer = customerMapper.toEntity(customerService.save(customerRequestTo));
        ScoringResponseTo scoringResponseTo = scoringService.evaluate(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(scoringResponseTo);
    }

}
