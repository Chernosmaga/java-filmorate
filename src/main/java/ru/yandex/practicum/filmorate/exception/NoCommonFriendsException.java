package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoCommonFriendsException extends RuntimeException {

    public NoCommonFriendsException(final String message) {
        log.error(message);
    }
}
