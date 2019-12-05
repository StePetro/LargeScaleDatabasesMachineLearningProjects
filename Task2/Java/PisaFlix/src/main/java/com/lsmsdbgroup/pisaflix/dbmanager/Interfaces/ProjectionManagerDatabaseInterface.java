package com.lsmsdbgroup.pisaflix.dbmanager.Interfaces;

import com.lsmsdbgroup.pisaflix.Entities.*;
import java.util.*;

public interface ProjectionManagerDatabaseInterface {

    void create(Date dateTime, int room, Film film, Cinema cinema);

    void delete(String idProjection);

    void update(String idProjection, Date dateTime, int room);

    Set<Projection> getAll();

    Projection getById(String projectionId);

    Set<Projection> queryProjection(String cinemaId, String filmId, String date, int room);

    boolean checkDuplicates(String cinemaId, String filmId, String date, int room);

}