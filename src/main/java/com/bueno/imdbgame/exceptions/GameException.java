package com.bueno.imdbgame.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = -8461094158956693516L;

    public GameException(String message) {
        super(message);
    }

    public GameException(Long id) {
        super(String.format("ID %d not found in database", id));
    }

}
