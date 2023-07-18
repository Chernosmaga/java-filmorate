package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashSet;

@Slf4j
@Service
public class FilmValidation {

    private Long enlarger(Long id) {
        return ++id;
    }

    public void validate(Film film) {
        if (film.getReleaseDate() == null ||
                film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Incorrect release date");
        }
        if (film.getName().isEmpty() || film.getName().isBlank()) {
            throw new ValidationException("Attempt to set an empty movie name");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Attempt to set duration less than zero");
        }
        if (film.getDescription().length() > 200 || film.getDescription().length() == 0) {
            throw new ValidationException("Description increases 200 symbols or empty");
        }
        if (film.getId() == null || film.getId() < 0) {
            film.setId(enlarger(film.getId()));
            log.info("Movie identifier was set as '{}", film.getId());
        }
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }
    }
}
