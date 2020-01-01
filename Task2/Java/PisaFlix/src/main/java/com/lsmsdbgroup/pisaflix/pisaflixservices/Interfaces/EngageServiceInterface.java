package com.lsmsdbgroup.pisaflix.pisaflixservices.Interfaces;

import com.lsmsdbgroup.pisaflix.Entities.Engage;
import com.lsmsdbgroup.pisaflix.Entities.Entity;
import com.lsmsdbgroup.pisaflix.Entities.Film;
import com.lsmsdbgroup.pisaflix.Entities.User;
import java.util.Set;

public interface EngageServiceInterface {
    void create(User user, Film film, Entity.EntityType type);

    void delete(String idEngage);
    
    Engage getById(String engageId);
    
    Set<Engage> getEngageSet(Entity entity, int limit, int skip, Entity.EntityType type);

    void deleteFiltred(User user, Film film, Entity.EntityType type);
    
    long count(Entity entity, Entity.EntityType type);

    boolean isAlreadyPresent(User userLogged, Film film, Entity.EntityType entityType);
}