package online.partyrun.partyrunauthenticationservice.global.exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException() {
        super("알 수 없는 에러입니다.");
    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}
