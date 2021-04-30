/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codenotfound.primefaces.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mac
 */
@Entity
@Table(name = "vehicule")
public class Vehicule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "matricule")
    private String matricule;
    @Basic(optional = false)
    @ManyToOne(optional = false)
    private Model model;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "couleur")
    private String couleur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prixjour")
    private float prixjour;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idvehicule")
    private List<Location> locationList;

    public Vehicule() {
    }

    public Vehicule(Long id) {
        this.id = id;
    }

    public Vehicule(Long id, String matricule, String couleur, float prixjour) {
        this.id = id;
        this.matricule = matricule;
        this.couleur = couleur;
        this.prixjour = prixjour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public float getPrixjour() {
        return prixjour;
    }

    public void setPrixjour(float prixjour) {
        this.prixjour = prixjour;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehicule)) {
            return false;
        }
        Vehicule other = (Vehicule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Vehicule[ id=" + id + " ]";
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}