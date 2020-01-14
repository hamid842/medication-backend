package com.app.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A MedicinInfo.
 */
@Entity
@Table(name = "medicin_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MedicinInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "important_info")
    private String importantInfo;

    @Column(name = "jhi_usage")
    private String usage;

    @Column(name = "initial_count")
    private String initialCount;

    @Column(name = "promised")
    private String promised;

    @Column(name = "refill_info")
    private Instant refillInfo;

    @Column(name = "pharmacy_notes")
    private String pharmacyNotes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MedicinInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImportantInfo() {
        return importantInfo;
    }

    public MedicinInfo importantInfo(String importantInfo) {
        this.importantInfo = importantInfo;
        return this;
    }

    public void setImportantInfo(String importantInfo) {
        this.importantInfo = importantInfo;
    }

    public String getUsage() {
        return usage;
    }

    public MedicinInfo usage(String usage) {
        this.usage = usage;
        return this;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getInitialCount() {
        return initialCount;
    }

    public MedicinInfo initialCount(String initialCount) {
        this.initialCount = initialCount;
        return this;
    }

    public void setInitialCount(String initialCount) {
        this.initialCount = initialCount;
    }

    public String getPromised() {
        return promised;
    }

    public MedicinInfo promised(String promised) {
        this.promised = promised;
        return this;
    }

    public void setPromised(String promised) {
        this.promised = promised;
    }

    public Instant getRefillInfo() {
        return refillInfo;
    }

    public MedicinInfo refillInfo(Instant refillInfo) {
        this.refillInfo = refillInfo;
        return this;
    }

    public void setRefillInfo(Instant refillInfo) {
        this.refillInfo = refillInfo;
    }

    public String getPharmacyNotes() {
        return pharmacyNotes;
    }

    public MedicinInfo pharmacyNotes(String pharmacyNotes) {
        this.pharmacyNotes = pharmacyNotes;
        return this;
    }

    public void setPharmacyNotes(String pharmacyNotes) {
        this.pharmacyNotes = pharmacyNotes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicinInfo)) {
            return false;
        }
        return id != null && id.equals(((MedicinInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MedicinInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", importantInfo='" + getImportantInfo() + "'" +
            ", usage='" + getUsage() + "'" +
            ", initialCount='" + getInitialCount() + "'" +
            ", promised='" + getPromised() + "'" +
            ", refillInfo='" + getRefillInfo() + "'" +
            ", pharmacyNotes='" + getPharmacyNotes() + "'" +
            "}";
    }
}
