/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lsmsdgroup.pisaflix;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author alessandromadonna
 */
@Entity
@Table(name = "Film")
@NamedQueries({
    @NamedQuery(name = "Film.findAll", query = "SELECT f FROM Film f")})
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFilm")
    private Integer idFilm;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "publicationDate")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;
    @Lob
    @Column(name = "description")
    private String description;
    @JoinTable(name = "Film_has_Rating", joinColumns = {
        @JoinColumn(name = "idFilm", referencedColumnName = "idFilm")}, inverseJoinColumns = {
        @JoinColumn(name = "idUser", referencedColumnName = "idUser")})
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<User> userCollection;
    @ManyToMany(mappedBy = "filmCollection", fetch = FetchType.EAGER)
    private Collection<Comment> commentCollection;
    @OneToMany(mappedBy = "idFilm", fetch = FetchType.EAGER)
    private Collection<Projection> projectionCollection;

    public Film() {
    }

    public Film(Integer idFilm) {
        this.idFilm = idFilm;
    }

    public Film(Integer idFilm, String title, Date publicationDate) {
        this.idFilm = idFilm;
        this.title = title;
        this.publicationDate = publicationDate;
    }

    public Integer getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Integer idFilm) {
        this.idFilm = idFilm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    public Collection<Projection> getProjectionCollection() {
        return projectionCollection;
    }

    public void setProjectionCollection(Collection<Projection> projectionCollection) {
        this.projectionCollection = projectionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFilm != null ? idFilm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Film)) {
            return false;
        }
        Film other = (Film) object;
        if ((this.idFilm == null && other.idFilm != null) || (this.idFilm != null && !this.idFilm.equals(other.idFilm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lsmsdgroup.pisaflix.Film[ idFilm=" + idFilm + " ]";
    }
    
}