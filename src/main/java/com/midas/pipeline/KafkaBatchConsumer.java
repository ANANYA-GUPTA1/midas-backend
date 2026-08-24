package com.midas.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class KafkaBatchConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaBatchConsumer.class);
    private final RiskValidationService validationService;
    private final TransactionRepository repository;

    public KafkaBatchConsumer(RiskValidationService validationService, TransactionRepository repository) {
        this.validationService = validationService;
        this.repository = repository;
    }

    @KafkaListener(topics = "synthetic-tx-stream", groupId = "risk-validation-group")
    public void consumeStream(List<Transaction> transactions) {
        logger.info("Received batch of {} transactions from Kafka", transactions.size());
        
        validationService.validateBatch(transactions);
        repository.saveAll(transactions);
        
        logger.info("Successfully validated and persisted batch to PostgreSQL.");
    }
}