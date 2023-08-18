package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ObjectAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmDbService;
import ru.yandex.practicum.filmorate.service.UserDbService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmControllerTest {
    private final FilmDbService filmService;
    private final UserDbService userService;
    private final User user = new User("user@ya.ru", "flying_dragon", "Andrew",
            LocalDate.of(1996, 12, 3));
    private final Film film = new Film("Ron's Gone Wrong", "The cartoon about a funny robot",
            LocalDate.of(2021, 10, 22), 107);
    private final Film updatedFilm = new Film("Ron's Gone Wrong",
            "The cartoon about a friendship between robot and a human",
            LocalDate.of(2021, 10, 22), 107);
    private final Film popularFilm = new Film("Titanic",
            "The movie about love", LocalDate.of(1997, 12, 19), 220);
    private final Mpa pg13 = new Mpa(3, "PG-13");
    private final Genre comedy = new Genre(3, "Comedy");

    @Test
    public void createFilm_shouldCreateFilm() {
        film.setMpa(pg13);
        film.setGenres(Set.of(comedy));
        filmService.createFilm(film);

        Assertions.assertFalse(filmService.getFilms().isEmpty());
    }

    @Test
    public void createFilm_shouldNotCreateFilmIfDescriptionTooLong() {
        film.setMpa(pg13);
        film.setGenres(Set.of(comedy));
        film.setDescription("This is the cartoon about a funny robot who always get into trouble." +
                "Other people though he is defective, but there was a guy who was happy to get him on his birthday." +
                "This cartoon show the future behaviour of our generation who grew up next to the phone and computer.");

        Assertions.assertThrows(ValidationException.class, () -> filmService.createFilm(film));
    }

    @Test
    public void updateFilm_shouldUpdateFilm() {
        film.setMpa(new Mpa(1));
        film.setGenres(Set.of(new Genre(1)));
        Film newFilm = filmService.createFilm(film);
        newFilm.setGenres(Set.of(new Genre(3), new Genre(2)));
        Film filmUpdated = filmService.updateFilm(newFilm);

        Assertions.assertEquals(filmService.getFilmById(newFilm.getId()).getName(),
                filmService.getFilmById(filmUpdated.getId()).getName());
    }

    @Test
    public void updateFilm_shouldNotUpdateFilmIfItHasNoTitle() {
        film.setMpa(new Mpa(1));
        film.setGenres(Set.of(new Genre(1)));
        filmService.createFilm(film);
        film.setName(null);

        Assertions.assertThrows(NullPointerException.class, () -> filmService.updateFilm(updatedFilm));
    }

    @Test
    public void getFilmById_shouldReturnFilm() {
        film.setMpa(new Mpa(1));
        film.setGenres(Set.of(new Genre(1)));
        Film newFilm = filmService.createFilm(film);

        Assertions.assertEquals(newFilm, filmService.getFilmById(newFilm.getId()));
    }

    @Test
    public void getFilmById_shouldNotReturnFilmIfIdIsIncorrect() {
        Assertions.assertThrows(ObjectNotFoundException.class, () -> filmService.getFilmById(145L));
    }

    @Test
    public void getFilms_shouldReturnListOfFilms() {
        film.setMpa(new Mpa(1));
        film.setGenres(Set.of(new Genre(1)));
        filmService.createFilm(film);
        popularFilm.setMpa(new Mpa(1));
        popularFilm.setGenres(Set.of(new Genre(1), new Genre(2)));
        filmService.createFilm(popularFilm);

        Assertions.assertEquals(2, filmService.getFilms().size());
    }

    @Test
    public void getFilms_shouldReturnAnEmptyListOfFilms() {
        Assertions.assertTrue(filmService.getFilms().isEmpty());
    }

    @Test
    public void getPopularMovies_shouldReturnListOfPopularMovies() {
        User newUser = userService.createUser(user);
        film.setMpa(new Mpa(3));
        film.setGenres(Set.of(new Genre(1), new Genre(2)));
        Film newFilm = filmService.createFilm(film);
        popularFilm.setMpa(new Mpa(4));
        popularFilm.setGenres(Set.of(new Genre(2), new Genre(3)));
        Film likedMovie = filmService.createFilm(popularFilm);
        filmService.like(likedMovie.getId(), newUser.getId());
        Collection<Film> films = filmService.getPopularMovies(1);

        Assertions.assertTrue(films.contains(likedMovie));
    }

    @Test
    public void getPopularMovies_shouldReturnAnEmptyListOfPopularMovies() {
        Assertions.assertTrue(filmService.getPopularMovies(1).isEmpty());
    }

    @Test
    public void like_shouldLikeAMovie() {
        User newUser = userService.createUser(user);
        film.setMpa(new Mpa(3));
        film.setGenres(Set.of(new Genre(1), new Genre(2)));
        Film newFilm = filmService.createFilm(film);
        filmService.like(newFilm.getId(), newUser.getId());

        Assertions.assertEquals(1, filmService.getPopularMovies(1).size());
    }

    @Test
    public void like_shouldNotLikeAMoviesIfItsAlreadyLikedByUser() {
        userService.createUser(user);
        filmService.createFilm(film);
        filmService.like(film.getId(), user.getId());

        Assertions.assertThrows(ObjectAlreadyExistsException.class,
                () -> filmService.like(film.getId(), user.getId()));
    }

    @Test
    public void dislike_shouldNotDislikeAMovieIfItWasNotLiked() {
        userService.createUser(user);
        filmService.createFilm(film);

        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> filmService.dislike(film.getId(), user.getId()));
    }
}
