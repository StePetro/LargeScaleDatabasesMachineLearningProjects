package com.lsmsdbgroup.pisaflix.Entities;

public abstract class Entity {
    public static enum EntityType {
     USER,
     FILM,
     COMMENT,
     FAVOURITE,
     VIEW
    }    
    public Entity(){}
    public abstract String getId();
}