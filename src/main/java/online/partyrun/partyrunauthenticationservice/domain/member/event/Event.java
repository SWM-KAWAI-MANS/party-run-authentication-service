package online.partyrun.partyrunauthenticationservice.domain.member.event;

public record Event(EventType type, Object value) {

    public static Event create(Object value) {
        return new Event(EventType.CREATED, value);
    }

    public static Event delete(Object value) {
        return new Event(EventType.DELETED, value);
    }

    public boolean isCreated() {
        return this.type.isCreated();
    }
}
