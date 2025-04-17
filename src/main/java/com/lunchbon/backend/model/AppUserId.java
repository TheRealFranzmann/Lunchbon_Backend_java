package com.lunchbon.backend.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class AppUserId implements Serializable {

    private static final long serialVersionUID = 3103408298830430734L;
	private Long generatedId;
    private Long domainUserId;

    public AppUserId() {
    }

    public AppUserId(Long generatedId, Long domainUserId) {
        this.generatedId = generatedId;
        this.domainUserId = domainUserId;
    }

    public Long getGeneratedId() {
        return generatedId;
    }

    public void setGeneratedId(Long generatedId) {
        this.generatedId = generatedId;
    }

    public Long getDomainUserId() {
        return domainUserId;
    }

    public void setDomainUserId(Long domainUserId) {
        this.domainUserId = domainUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUserId)) return false;
        AppUserId that = (AppUserId) o;
        return Objects.equals(generatedId, that.generatedId) &&
               Objects.equals(domainUserId, that.domainUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generatedId, domainUserId);
    }
}
