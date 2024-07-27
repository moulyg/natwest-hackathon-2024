package com.natwest.boa.hackathon.repository;

import com.natwest.boa.hackathon.model.cashback.Cashback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashbackRepository extends JpaRepository<Cashback, Long> {

    List<Cashback> findByAccountNo(String accountNo);
}
