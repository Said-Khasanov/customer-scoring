package ru.khasanov.customerscoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khasanov.customerscoring.entity.Scoring;

public interface ScoringRepository extends JpaRepository<Scoring, Long> {
}
