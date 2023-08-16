package online.partyrun.partyrunauthenticationservice.domain.member.event;

public enum EventType {
    CREATED;

    public boolean isCreated() {
        return this == CREATED;
    }
}
