package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users;
    private final UserValidation validation;

    @Override
    public void createUser(User user) {
        validation.validate(user);
        users.put(user.getId(), user);
        log.info("The user '{}' has been saved with the identifier '{}'", user.getEmail(), user.getId());
    }

    @Override
    public void updateUser(User user) {
        if (users.containsKey(user.getId())) {
            validation.validate(user);
            users.put(user.getId(), user);
            log.info("'{}' info with identifier '{}' was updated", user.getLogin(), user.getId());
        } else {
            throw new ValidationException("Attempt to update non-existing user");
        }
    }

    @Override
    public void deleteUsers() {
        users.clear();
        log.info("User storage is empty now");
    }

    @Override
    public User getUserById(Long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new ObjectNotFoundException("Attempt to reach non-existing user with id '" + id + "'");
        }
    }

    @Override
    public Map<Long, User> getUsers() {
        log.info("The number of users: '{}'", users.size());
        return users;
    }
}
