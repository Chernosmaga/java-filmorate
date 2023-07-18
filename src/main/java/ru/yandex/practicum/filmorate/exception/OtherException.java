package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OtherException extends RuntimeException {

    public OtherException(final String message) {
        log.error(message);
    }
}
