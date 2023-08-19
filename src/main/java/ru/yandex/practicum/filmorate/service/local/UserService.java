package ru.yandex.practicum.filmorate.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.user.UserStorage;
import ru.yandex.practicum.filmorate.storage.local.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final InMemoryUserStorage userStorage;

    public void addFriend(Long userId, Long friendId) {
        userStorage.addFriend(userId, friendId);
        userStorage.addFriend(friendId, userId);
        log.info("'{}' added '{}' to a friend list", userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        userStorage.removeFriend(userId, friendId);
        userStorage.removeFriend(friendId, userId);
        log.info("'{}' removed '{}' from friends list", userId, friendId);
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        List<User> userFriends = userStorage.getFriends(userId);
        List<User> friendFriends = userStorage.getFriends(friendId);
        log.info("'{}' requested common friends' list with user '{}'", userId, friendId);
        if (userFriends.stream().anyMatch(friendFriends::contains)) {
            return userFriends.stream()
                    .filter(userFriends::contains)
                    .filter(friendFriends::contains).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public List<User> getFriends(Long userId) {
        List<User> friends = userStorage.getFriends(userId);
        if (friends.isEmpty()) {
            throw new ObjectNotFoundException("User's friends' list with id '" + userId + "' is empty");
        }
        return friends;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }
}
