package online.partyrun.partyrunauthenticationservice.domain.member.event;

public record Event(EventType type, Object value) {

    public static Event create(Object value) {
        return new Event(EventType.CREATED, value);
    }
}
