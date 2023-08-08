package online.partyrun.partyrunauthenticationservice.global.exception;

public abstract class NotFoundException extends RuntimeException {
    protected NotFoundException() {
        super("요청한 리소스를 찾을 수 없습니다.");
    }

    protected NotFoundException(String message) {
        super(message);
    }
}
