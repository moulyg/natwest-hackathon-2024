package com.natwest.boa.hackathon.service;

@org.springframework.stereotype.Service
public class RewardsService {

    public int calculateRewardCashBack(double amount) {
        return (int) (amount * 0.01); // 1% of the amount
    }
}
