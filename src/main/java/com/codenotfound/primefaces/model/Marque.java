package com.codenotfound.primefaces.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Marque
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

    @OneToMany(mappedBy = "marque", fetch = FetchType.EAGER)
    private List<Model> models;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getLibelle()
    {
        return libelle;
    }

    public void setLibelle(String libelle)
    {
        this.libelle = libelle;
    }

    public List<Model> getModels()
    {
        return models;
    }

    public void setModels(List<Model> models)
    {
        this.models = models;
    }
}