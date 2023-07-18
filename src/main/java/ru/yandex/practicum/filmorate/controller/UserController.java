package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    @PostMapping
    public void createUser(@Valid @RequestBody User user) {
        userStorage.createUser(user);
    }

    @GetMapping
    public Map<Long, User> getUsers() {
        return userStorage.getUsers();
    }

    @PutMapping
    public void updateUser(@Valid @RequestBody User user) {
        userStorage.updateUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userStorage.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Long userId) {
        return userService.getFriends(userId);
    }

    @GetMapping("users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Long userId, @PathVariable Long friendId) {
        return userService.getCommonFriends(userId, friendId);
    }
}