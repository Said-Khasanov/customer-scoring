package ru.khasanov.customerscoring.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.khasanov.customerscoring.dto.ScoringResponseTo;
import ru.khasanov.customerscoring.entity.Customer;
import ru.khasanov.customerscoring.entity.Scoring;
import ru.khasanov.customerscoring.mapper.ScoringMapper;
import ru.khasanov.customerscoring.repository.ScoringRepository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ScoringService {
    public static final String RESULT_OK = "OK";
    private final ScoringRepository scoringRepository;
    private final ScoringMapper scoringMapper;
    private final DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
    @Value("${spring.application.decision-id}")
    public String DECISION_ID;
    @Value("${spring.application.dmn-model-file}")
    public String DMN_MODEL_FILE;

    public ScoringResponseTo evaluate(Customer customer) {
        InputStream dmnModel = getClass().getResourceAsStream(DMN_MODEL_FILE);
        DmnDecision decision = dmnEngine.parseDecision(DECISION_ID, dmnModel);

        Map<String, Object> variables = new HashMap<>();
        variables.put("inn", customer.getInn());
        variables.put("region", customer.getRegion());
        variables.put("capital", customer.getCapital().doubleValue());

        DmnDecisionTableResult dmnDecisionTableResult = dmnEngine.evaluateDecisionTable(decision, variables);
        Scoring scoring = fillScoring(dmnDecisionTableResult, customer);
        return save(scoring);
    }

    public List<ScoringResponseTo> findAll() {
        return scoringRepository.findAll()
                .stream()
                .map(scoringMapper::toResponseTo)
                .toList();
    }

    public ScoringResponseTo save(Scoring scoring) {
        return scoringMapper.toResponseTo(scoringRepository.save(scoring));
    }

    private Scoring fillScoring(DmnDecisionTableResult dmnDecisionTableResult, Customer customer) {
        boolean result = dmnDecisionTableResult.size() != 1;
        String innResult = getStringResultByEntryId(dmnDecisionTableResult, "innResult");
        String regionResult = getStringResultByEntryId(dmnDecisionTableResult, "regionResult");
        String capitalResult = getStringResultByEntryId(dmnDecisionTableResult, "capitalResult");
        String residentResult = getStringResultByEntryId(dmnDecisionTableResult, "residentResult");

        return Scoring.builder()
                .customer(customer)
                .result(result)
                .innResult(innResult)
                .regionResult(regionResult)
                .capitalResult(capitalResult)
                .residentResult(residentResult)
                .build();
    }

    private String getStringResultByEntryId(DmnDecisionTableResult dmnDecisionTableResult, String entryId) {
        return dmnDecisionTableResult.collectEntries(entryId)
                .stream()
                .filter(o -> !o.toString().equals(RESULT_OK))
                .findFirst()
                .orElse(RESULT_OK)
                .toString();
    }

}
