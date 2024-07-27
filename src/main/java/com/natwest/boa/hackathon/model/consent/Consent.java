package com.natwest.boa.hackathon.model.consent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "consents")
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "consent_id", nullable = false)
    private String consentId;

    @Column(name = "consent_data", columnDefinition = "TEXT")
    private String consentData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public String getConsentData() {
        return consentData;
    }

    public void setConsentData(String consentData) {
        this.consentData = consentData;
    }

    @Override
    public String toString() {
        return "Consent{" +
                "id=" + id +
                ", consentId='" + consentId + '\'' +
                ", consentData='" + consentData + '\'' +
                '}';
    }
}


