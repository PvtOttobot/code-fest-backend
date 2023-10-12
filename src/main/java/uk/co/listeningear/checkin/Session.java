package uk.co.listeningear.checkin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.stream.Stream;

@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;

    @JoinColumn(name = "therapist_id")
    @ManyToOne
    private User therapist;

    @JoinColumn(name = "admin_id")
    @ManyToOne
    private User admin;

    @Column(name = "status")
    private String statusValue;

    @Transient
    private Status status;

    private String location;

    @Column(name = "expected_start")
    private OffsetDateTime expectedStart;

    @Column(name = "expected_end")
    private OffsetDateTime expectedEnd;

    @Column(name = "started_at")
    private OffsetDateTime startedAt;

    @Column(name = "ended_at")
    private OffsetDateTime endedAt;

    public User getTherapist() {
        return therapist;
    }

    public void setTherapist(User therapist) {
        this.therapist = therapist;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    @PostLoad
    public void postLoad() {
        if (statusValue != null) {
            status = Status.fromInternalValue(statusValue);
        }
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (status != null) {
            statusValue = status.getInternalValue();
        }
    }

    @Override
    public String toString() {
        return "Session{" +
               "id=" + id +
               ", therapist=" + therapist +
               ", admin=" + admin +
               ", statusValue='" + statusValue + '\'' +
               ", status=" + status +
               ", location='" + location + '\'' +
               ", expectedStart=" + expectedStart +
               ", expectedEnd=" + expectedEnd +
               ", startedAt=" + startedAt +
               ", endedAt=" + endedAt +
               '}';
    }

    public enum Status {

        SCHEDULED("Scheduled"),
        IN_PROGRESS("In progress"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled");

        private final String internalValue;

        Status(String internalValue) {
            this.internalValue = internalValue;
        }

        private String getInternalValue() {
            return internalValue;
        }

        private static Status fromInternalValue(String internalValue) {
            return Stream.of(Status.values())
                    .filter(status -> status.getInternalValue().equals(internalValue))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

}
