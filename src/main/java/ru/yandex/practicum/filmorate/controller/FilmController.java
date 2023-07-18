package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private FilmStorage filmStorage;
    private FilmService filmService;

    @PostMapping
    public void createFilm(@Valid @RequestBody Film film) {
        filmStorage.createFilm(film);
    }

    @GetMapping
    public Map<Long, Film> getFilms() {
        return filmStorage.getFilms();
    }

    @PutMapping
    public void updateFilm(@Valid @RequestBody Film film) {
        filmStorage.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return filmStorage.getFilmById(id);
    }

    @GetMapping("/popular?count={count}")
    public List<Film> getPopularMovies(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.getPopularMovies(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeAMovie(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.like(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.dislike(filmId, userId);
    }
}