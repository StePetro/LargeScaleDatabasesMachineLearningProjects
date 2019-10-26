package com.lsmsdgroup.pisaflix;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

@Entity
@Table(name = "User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUser")
    private Integer idUser;
    
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "firstName")
    private String firstName;
    
    @Column(name = "lastName")
    private String lastName;
    
    @Basic(optional = false)
    @Column(name = "privilegeLevel")
    private int privilegeLevel;
    
    @ManyToMany(mappedBy = "userCollection", fetch = FetchType.EAGER,cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private Collection<Cinema> cinemaCollection;
    
    @ManyToMany(mappedBy = "userCollection", fetch = FetchType.EAGER,cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private Collection<Film> filmCollection;
    
    @OneToMany(mappedBy = "idUser", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Collection<Comment> commentCollection;

    public User() {
    }

    public User(Integer idUser) {
        this.idUser = idUser;
    }

    public User(Integer idUser, String username, String password, int privilegeLevel) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.privilegeLevel = privilegeLevel;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPrivilegeLevel() {
        return privilegeLevel;
    }

    public void setPrivilegeLevel(int privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
    }

    public Collection<Cinema> getCinemaCollection() {
        return cinemaCollection;
    }

    public void setCinemaCollection(Collection<Cinema> cinemaCollection) {
        this.cinemaCollection = cinemaCollection;
    }
    
    public void addFavouriteCinema(Cinema cinema){
        cinemaCollection.add(cinema);
        cinema.getUserCollection().add(this);
        DBManager.UserManager.updateFavorites(this);
    }

    public Collection<Film> getFilmCollection() {
        return filmCollection;
    }

    public void setFilmCollection(Collection<Film> filmCollection) {
        this.filmCollection = filmCollection;
    }
    
    public void addFavouriteFilm(Film film){
        filmCollection.add(film);
        film.getUserCollection().add(this);
        DBManager.UserManager.updateFavorites(this);
    }

    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "\n User[  idUser=" + idUser + " | Username="+username+" | First Name="+firstName+" | Last Name="+lastName+" | Email="+email+" | Prvilege Level="+privilegeLevel+"] ";
    }
    
}
