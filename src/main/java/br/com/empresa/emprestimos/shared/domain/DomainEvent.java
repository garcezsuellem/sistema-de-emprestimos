package br.com.empresa.emprestimos.shared.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

public abstract class DomainEvent {

    private final UUID eventId;
    private final OffsetDateTime occurredOn;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredOn = OffsetDateTime.now();
    }

    public UUID getEventId() {
        return eventId;
    }

    public OffsetDateTime getOccurredOn() {
        return occurredOn;
    }
}