package online.partyrun.partyrunauthenticationservice.domain.auth.exception;

import online.partyrun.partyrunauthenticationservice.global.exception.BadRequestException;

public class IllegalIdTokenException extends BadRequestException {
    public IllegalIdTokenException(String message) {
        super(message);
    }
}
