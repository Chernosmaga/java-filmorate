package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {

    void createUser(User user);

    void updateUser(User user);

    void deleteUsers();

    User getUserById(Long id);

    Map<Long, User> getUsers();

}
