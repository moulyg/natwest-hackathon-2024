package com.natwest.boa.hackathon.service;

import com.natwest.boa.hackathon.model.cashback.Cashback;
import com.natwest.boa.hackathon.repository.CashbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CashbackService {

    @Autowired
    private CashbackRepository cashbackRepository;


    public Cashback calculateRewardCashBack(double amount, String paymentId, String accountNumber) {
        double cashback = amount * 0.01; // 1% of the amount
        return saveCashback(accountNumber, paymentId, cashback, amount);
    }

    public Cashback saveCashback(String accountNo, String paymentId, double cashbackValue, double transactionAmount) {
        Cashback cashback = new Cashback(accountNo, paymentId, BigDecimal.valueOf(cashbackValue), BigDecimal.valueOf(transactionAmount), LocalDateTime.now());
        return cashbackRepository.save(cashback);
    }

    public Map<String, Object> getCashbackDetailsForAccount(String accountNo) {
        List<Cashback> cashBacks = cashbackRepository.findByAccountNo(accountNo);
        BigDecimal totalCashback = cashBacks.stream()
                .map(Cashback::getCashbackValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Map.of(
                "totalCashback", totalCashback,
                "cashBacks", cashBacks
        );
    }
}
