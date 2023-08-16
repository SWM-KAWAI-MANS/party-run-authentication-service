package online.partyrun.partyrunauthenticationservice.domain.member.event;

public enum EventType {
    CREATED, DELETED;

    public boolean isCreated() {
        return this == CREATED;
    }
}
