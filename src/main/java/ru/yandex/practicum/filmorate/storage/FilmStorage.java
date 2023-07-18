package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

public interface FilmStorage {

    void createFilm(Film film);

    void updateFilm(Film film);

    void deleteFilms();

    Film getFilmById(Long id);

    Map<Long, Film> getFilms();

}
