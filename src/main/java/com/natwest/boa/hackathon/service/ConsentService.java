package com.natwest.boa.hackathon.service;

import com.natwest.boa.hackathon.model.consent.Consent;
import com.natwest.boa.hackathon.repository.ConsentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ConsentService {

    @Autowired
    private ConsentRepository repository;

    public Consent saveConsent(String consentId, String consentData) {
        Consent consent = new Consent();
        consent.setConsentId(consentId);
        consent.setConsentData(consentData);
        return repository.save(consent);
    }

    public Consent getConsentById(Long id) {
        return repository.findById(id).orElse(null);
    }

}
