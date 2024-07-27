package com.natwest.boa.hackathon.model.cashback;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Cashback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountNo;

    @Column(nullable = false)
    private String paymentId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cashbackValue;


    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal transactionAmount;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    // Constructors
    public Cashback() {}

    public Cashback(String accountNo, String paymentId, BigDecimal cashbackValue, BigDecimal transactionAmount, LocalDateTime createdDate) {
        this.accountNo = accountNo;
        this.paymentId = paymentId;
        this.cashbackValue = cashbackValue;
        this.transactionAmount = transactionAmount;
        this.createdDate = createdDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getCashbackValue() {
        return cashbackValue;
    }

    public void setCashbackValue(BigDecimal cashbackValue) {
        this.cashbackValue = cashbackValue;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Cashback{" +
                "id=" + id +
                ", accountNo='" + accountNo + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", cashbackValue=" + cashbackValue +
                ", transactionAmount=" + transactionAmount +
                ", createdDate=" + createdDate +
                '}';
    }
}
