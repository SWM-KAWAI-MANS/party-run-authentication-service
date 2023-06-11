package online.partyrun.partyrunauthenticationservice.domain.auth.exception;

import online.partyrun.partyrunauthenticationservice.global.exception.BadRequestException;

public class NoSuchRefreshTokenException extends BadRequestException {
    public NoSuchRefreshTokenException(String refreshToken) {
        super(refreshToken + "을 찾을 수 없습니다.");
    }
}
