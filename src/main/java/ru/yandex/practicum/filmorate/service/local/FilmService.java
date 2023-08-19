package ru.yandex.practicum.filmorate.service.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.local.InMemoryFilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final UserService userService;

    public void like(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        userService.getUserStorage().getUserById(userId);
        if (film == null) {
            throw new ObjectNotFoundException("Attempt to reach non-existing movie with id '" + filmId + "'");
        }
        filmStorage.addLike(filmId, userId);
        log.info("'{}' liked a movie '{}'", userId, filmId);
    }

    public void dislike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        userService.getUserStorage().getUserById(userId);
        if (film == null) {
            throw new ObjectNotFoundException("Attempt to reach non-existing movie with id '" + filmId + "'");
        }
        filmStorage.removeLike(filmId, userId);
        log.info("'{}' disliked a movie '{}'", userId, filmId);
    }

    public List<Film> getPopularMovies(int count) {
        log.info("Attempt to get the most liked movies list");
        return filmStorage.getFilms()
                .stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    private int compare(Film film, Film otherFilm) {
        return Integer.compare(
                filmStorage.getLikesQuantity(otherFilm.getId()), filmStorage.getLikesQuantity(film.getId()));
    }
}
