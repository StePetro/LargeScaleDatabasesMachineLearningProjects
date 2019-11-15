package com.lsmsdbgroup.pisaflix.pisaflixservices;

import com.lsmsdbgroup.pisaflix.Entities.*;
import com.lsmsdbgroup.pisaflix.pisaflixservices.exceptions.*;
import java.util.Date;
import java.util.Set;
import com.lsmsdbgroup.pisaflix.dbmanager.Interfaces.FilmManagerDatabaseInterface;
import com.lsmsdbgroup.pisaflix.pisaflixservices.Interfaces.*;

public class FilmService implements FilmServiceInterface {

    private final FilmManagerDatabaseInterface fm;
    private final UserServiceInterface us;

    FilmService(FilmManagerDatabaseInterface filmManager, UserServiceInterface userService) {
        fm = filmManager;
        us = userService;
    }

    @Override
    public Set<Film> getFilmsFiltered(String titleFilter, Date startDateFilter, Date endDateFilter) {
        Set<Film> films = null;
        films = fm.getFiltered(titleFilter, startDateFilter, endDateFilter);
        return films;
    }

    @Override
    public Set<Film> getAll() {
        Set<Film> films = null;
        films = fm.getAll();
        return films;
    }

    @Override
    public Film getById(int id) {
        Film film;
        film = fm.getById(id);
        return film;
    }

    @Override
    public void addFilm(String title, Date publicationDate, String description) {
        if (title == null || title.isBlank()) {
            System.out.println("Il titolo non può essere vuoto");
            return;
        }
        if (publicationDate == null) {
            System.out.println("la data non può essere vuota");
            return;
        }
        if (description == null) {
            System.out.println("la descrizione non può essere vuota");
            return;
        }
        fm.create(title, publicationDate, description);
    }

    @Override
    public void deleteFilm(int idFilm) throws UserNotLoggedException, InvalidPrivilegeLevelException {
        us.checkUserPrivilegesForOperation(UserPrivileges.MODERATOR, "delete a film");
        fm.delete(idFilm);
    }

    @Override
    public void addFavorite(Film film, User user) {
        user.getFilmSet().add(film);
        film.getUserSet().add(user);
        fm.updateFavorites(film);
    }

    @Override
    public void removeFavourite(Film film, User user) {
        user.getFilmSet().remove(film);
        film.getUserSet().remove(user);
        fm.updateFavorites(film);
    }

}
