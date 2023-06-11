package online.partyrun.partyrunauthenticationservice.global.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("승인되지 않은 요청입니다.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
