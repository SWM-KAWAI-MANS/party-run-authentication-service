package online.partyrun.partyrunauthenticationservice.domain.member.dto;

public record MessageResponse(String message) {

    private static final String DEFAULT_MESSAGE = "요청이 정상적으로 처리되었습니다.";

    public MessageResponse() {
        this(DEFAULT_MESSAGE);
    }
}
