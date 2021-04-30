package com.codenotfound.primefaces.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Model
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "libelle")
    private String libelle;

    @ManyToOne(optional = false)
    private Marque marque;

    @OneToMany(mappedBy = "model")
    private List<Vehicule> vehicules;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Marque getMarque()
    {
        return marque;
    }

    public String getLibelle()
    {
        return libelle;
    }

    public void setLibelle(String libelle)
    {
        this.libelle = libelle;
    }

    public void setMarque(Marque marque)
    {
        this.marque = marque;
    }

    public List<Vehicule> getVehicules()
    {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules)
    {
        this.vehicules = vehicules;
    }
}