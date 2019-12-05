package com.lsmsdbgroup.pisaflix.pisaflixservices.Interfaces;

import com.lsmsdbgroup.pisaflix.Entities.*;
import com.lsmsdbgroup.pisaflix.pisaflixservices.exceptions.*;
import java.util.*;

public interface ProjectionServiceInterface {

    void addProjection(Cinema c, Film f, Date d, int room) throws UserNotLoggedException, InvalidPrivilegeLevelException, InvalidFieldException;

    void removeProjection(String projectionId);

    Set<Projection> queryProjections(String cinemaId, String filmId, String date, int room);
}