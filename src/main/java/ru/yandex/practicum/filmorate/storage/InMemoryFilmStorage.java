package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.EntityValidation;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films;
    private final EntityValidation entityValidation;

    @Override
    public void createFilm(Film film) {
        entityValidation.validate(film);
        films.put(film.getId(), film);
        log.info("'{}' movie was added to a library, the identifier is '{}'", film.getName(), film.getId());
    }

    @Override
    public void updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("'{}' movie was updated in a library, the identifier is '{}'", film.getName(), film.getId());
        } else {
            throw new ValidationException("Attempt to update non-existing movie");
        }
    }

    @Override
    public void deleteFilms() {
        films.clear();
        log.info("Movie storage is empty now");
    }

    @Override
    public Film getFilmById(Long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new ObjectNotFoundException("Attempt to reach non-existing movie with id '" + id + "'");
        }
    }

    @Override
    public Map<Long, Film> getFilms() {
        log.info("There are '{}' movies in a library now", films.size());
        return films;
    }
}
