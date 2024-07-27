package com.natwest.boa.hackathon.repository;

import com.natwest.boa.hackathon.model.consent.Consent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentRepository extends JpaRepository<Consent, Long> {
    Consent findByConsentId(String consentId);
}
