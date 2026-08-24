package com.midas.pipeline;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RiskValidationService {
    
    public void validateBatch(List<Transaction> transactions) {
        for (Transaction tx : transactions) {
            // Flag unusually high synthetic amounts
            boolean flagged = tx.getTransactionAmount() != null && tx.getTransactionAmount() > 5000.0;
            tx.setIsFlagged(flagged);
        }
    }
}