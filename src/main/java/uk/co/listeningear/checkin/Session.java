package uk.co.listeningear.checkin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
public class Session {

    @Id
    private BigDecimal id;

    @Column(name="therapist_id")
    private BigDecimal therapistId;

    @Column(name="admin_id")
    private BigDecimal adminId;

    private String status;

    private String location;

    @Column(name="expected_start")
    private OffsetDateTime expectedStart;

    @Column(name="expected_end")
    private OffsetDateTime expectedEnd;

    @Column(name="started_at")
    private OffsetDateTime startedAt;

    @Column(name="ended_at")
    private OffsetDateTime endedAt;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(BigDecimal therapistId) {
        this.therapistId = therapistId;
    }

    public BigDecimal getAdminId() {
        return adminId;
    }

    public void setAdminId(BigDecimal adminId) {
        this.adminId = adminId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public OffsetDateTime getExpectedStart() {
        return expectedStart;
    }

    public void setExpectedStart(OffsetDateTime expectedStart) {
        this.expectedStart = expectedStart;
    }

    public OffsetDateTime getExpectedEnd() {
        return expectedEnd;
    }

    public void setExpectedEnd(OffsetDateTime expectedEnd) {
        this.expectedEnd = expectedEnd;
    }

    public OffsetDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(OffsetDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public OffsetDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(OffsetDateTime endedAt) {
        this.endedAt = endedAt;
    }

}
