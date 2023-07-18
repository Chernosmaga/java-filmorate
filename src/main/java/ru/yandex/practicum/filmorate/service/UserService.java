package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NoCommonFriendsException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public void addFriend(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);
        log.info("'{}' added '{}' to a friend list", userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.removeFriend(friendId);
        friend.removeFriend(userId);
        log.info("'{}' removed '{}' from friends list", userId, friendId);
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        Set<Long> userFriends = user.getFriends();
        Set<Long> friendFriends = friend.getFriends();
        log.info("'{}' requested common friends' list with user '{}'", userId, friendId);
        if (userFriends.stream().anyMatch(friendFriends::contains)) {
            return userFriends.stream()
                    .filter(userFriends::contains)
                    .filter(friendFriends::contains)
                    .map(this::getUserById).collect(Collectors.toList());
        } else {
            throw new NoCommonFriendsException("No common friend were found" +
                    "for user with the identifier '" + userId + "' with user with id '" + friendId + "'");
        }
    }

    public List<User> getFriends(Long userId) {
        User user = getUserById(userId);
        Set<Long> friends = user.getFriends();
        if (friends.isEmpty()) {
            throw new ObjectNotFoundException("User's friends' list with id '" + userId + "' is empty");
        }
        return friends.stream()
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    private User getUserById(Long userId) {
        User user = userStorage.getUsers().getOrDefault(userId, null);
        if (user == null) {
            throw new ObjectNotFoundException("Attempt to reach non-existing user with id '" + userId + "'");
        } else {
            return user;
        }
    }
}
