package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;

@Slf4j
@Service
public class UserValidation {

    private Long enlarger(Long id) {
        return ++id;
    }

    public void validate(User user) {
        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday() == null) {
            throw new ValidationException("Incorrect user's birthday with identifier '" + user.getId() + "'");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Incorrect user's email with identifier '" + user.getId() + "'");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("User's name with identifier '{}' was set as '{}'", user.getId(), user.getName());
        }
        if (user.getId() == null || user.getId() <= 0) {
            user.setId(enlarger(0L));
            log.info("User's identifier was set as '{}'", user.getId());
        }
        if (user.getLogin().isBlank() || user.getLogin().isEmpty()) {
            throw new ValidationException("Incorrect user's login with identifier '" + user.getId() + "'");
        }
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
    }
}
