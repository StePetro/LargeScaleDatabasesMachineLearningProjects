package com.lsmsdgroup.pisaflix;

import java.sql.Timestamp;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.hibernate.Session;

public class DBManager {

    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    public static void setup() {
        factory = Persistence.createEntityManagerFactory("PisaFlix");

    }

    public static void exit() {
        factory.close();
    }

    public static class UserManager {

        public static User getById(int userId) {
            // code to get a user
            User user = null;
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                user = entityManager.find(User.class, userId);
                if (user == null) {
                    System.out.println("User not found!");
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
                System.out.println("A problem occurred in retriving a user!");
            } finally {
                entityManager.close();
            }
            return user;
        }

        public static void create(String username, String password, int privilegeLevel) {
            // code to create a user
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setPrivilegeLevel(privilegeLevel);
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                entityManager.persist(user);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("A problem occurred in creating the user!");
            } finally {
                entityManager.close();
            }
        }
        
        public static void updateFavorites(User user){
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                entityManager.merge(user);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("A problem occurred adding in favorite!");
            } finally {
                entityManager.close();
            }
        }

        public static void delete(int userId) {
            // code to delete a user
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                User reference = entityManager.getReference(User.class, userId);
                entityManager.remove(reference);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("A problem occurred in removing a User!");
            } finally {
                entityManager.close();
            }
        }

        public static void update(int userId, String username, String password, int privilegeLevel) {
            // code to update a user
            User user = new User(userId);
            user.setUsername(username);
            user.setPassword(password);
            user.setPrivilegeLevel(privilegeLevel);
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                entityManager.merge(user);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("A problem occurred in updating a user!");

            } finally {
                entityManager.close();
            }
        }

        public static List<User> getAllUsers() {
            // code to retrieve all users
            System.out.println("Retrieving users");
            List<User> users = null;
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                users = entityManager.createQuery("SELECT u FROM User u").getResultList();
                if (users == null) {
                    System.out.println("User is empty!");
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
                System.out.println("A problem occurred in retriving a user!");
            } finally {
                entityManager.close();
            }
            return users;
        }
        
    }

    public static class FilmManager {

        public static Film getById(int filmId){
            Film film = null;      
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                film = entityManager.find(Film.class, filmId);
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
                System.out.println("A problem occurred in retriving a film!");
            } finally {
                entityManager.close();
            } 
            return film;
        }
        
        public static List<Film> getAll(){
            List<Film> films = null;        
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();    
                films = entityManager.createQuery("FROM Film").getResultList();
                if (films == null) {
                    System.out.println("Film is empty!");
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
                System.out.println("A problem occurred in retrieve all films!");
            } finally {
                entityManager.close();
            }  
            return films;
        }
        
        public static void create(String title, Date publicationDate, String description){
            Film film = new Film();
            film.setTitle(title);
            film.setDescription(description);
            film.setPublicationDate(publicationDate);           
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                entityManager.persist(film);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("A problem occurred in creating the film!");
            } finally {
                entityManager.close();
            }
        }
        
        public static void update(int idFilm, String title, Date publicationDate, String description){
            Film film = new Film(idFilm);
            film.setTitle(title);
            film.setDescription(description);
            film.setPublicationDate(publicationDate); 
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                entityManager.merge(film);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("A problem occurred in updating the film!");
            } finally {
                entityManager.close();
            }
        }
        
        public static void delete(int idFilm){
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                Film reference = entityManager.getReference(Film.class, idFilm);
                entityManager.remove(reference);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("A problem occurred in deleting the film!");
            } finally {
                entityManager.close();
            } 
        }

    }
    
    public static class CinemaManager{
        
        public static void create(String name, String address) {
            Cinema cinema = new Cinema();
            cinema.setName(name);
            cinema.setAddress(address);
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                entityManager.persist(cinema);
                entityManager.getTransaction().commit();
                System.out.println("Cinema Added");
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("A problem occurred in creating the cinema!");
            } finally {
                entityManager.close();
            }
        }
        
        public static Cinema getById(int cinemaId){
            Cinema cinema = null;      
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                cinema = entityManager.find(Cinema.class, cinemaId);       
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
                System.out.println("A problem occurred in retriving a film!");
            } finally {
                entityManager.close();
            }       
            return cinema;
        }
        
    }

    public static class CommentManager{
        
        public static void createFilmComment(String text, User user, Film film){
            Comment comment = new Comment();
            comment.setText(text);
            comment.setTimestamp(new Date());

            user.getCommentCollection().add(comment);
            film.getCommentCollection().add(comment);
            Collection<Film> filmCollection = new ArrayList<>();
            filmCollection.add(film);
            comment.setFilmCollection(filmCollection);
            comment.setIdUser(user);
            
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                entityManager.persist(comment);
                entityManager.merge(user);
                entityManager.merge(film);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("A problem occurred in creating the comment!");
            } finally {
                entityManager.close();
            }
        }
        
        public static void createCinemaComment(String text, User user, Cinema cinema){
            Comment comment = new Comment();
            comment.setText(text);
            comment.setTimestamp(new Date());

            user.getCommentCollection().add(comment);
            cinema.getCommentCollection().add(comment);
            Collection<Cinema> cinemaCollection = new ArrayList<>();
            cinemaCollection.add(cinema);
            comment.setCinemaCollection(cinemaCollection);
            comment.setIdUser(user);
            
            try {
                entityManager = factory.createEntityManager();
                entityManager.getTransaction().begin();
                entityManager.persist(comment);
                entityManager.merge(user);
                entityManager.merge(cinema);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("A problem occurred in creating the comment!");
            } finally {
                entityManager.close();
            }
        }
    
    }
}
