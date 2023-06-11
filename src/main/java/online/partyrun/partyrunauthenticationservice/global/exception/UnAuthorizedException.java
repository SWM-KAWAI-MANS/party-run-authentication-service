package online.partyrun.partyrunauthenticationservice.global.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super("승인되지 않은 요청입니다.");
    }

    public UnAuthorizedException(String message) {
        super(message);
    }
}
