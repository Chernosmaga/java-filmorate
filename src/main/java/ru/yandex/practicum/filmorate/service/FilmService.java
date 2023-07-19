package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
     private final FilmStorage filmStorage;
     private final UserStorage userStorage;

     @Autowired
     public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
     }

    public void like(Long filmId, Long userId) {
         Film film = filmStorage.getFilmById(filmId);
         userStorage.getUserById(userId);
         if (film == null) {
             throw new ObjectNotFoundException("Attempt to reach non-existing movie with id '" + filmId + "'");
         }
         film.addLike(userId);
         log.info("'{}' liked a movie '{}'", userId, filmId);
     }

     public void dislike(Long filmId, Long userId) {
         Film film = filmStorage.getFilmById(filmId);
         userStorage.getUserById(userId);
         if (film == null) {
             throw new ObjectNotFoundException("Attempt to reach non-existing movie with id '" + filmId + "'");
         }
         film.removeLike(userId);
         log.info("'{}' disliked a movie '{}'", userId, filmId);
     }

     public List<Film> getPopularMovies(int count) {
         log.info("Attempt to get the most liked movies list");
         return filmStorage.getFilms().stream()
                 .sorted(Comparator.comparingInt(Film::getLikesQuantity).reversed())
                 .limit(count).collect(Collectors.toList());
     }
}
