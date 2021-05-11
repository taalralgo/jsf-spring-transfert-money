

package com.codenotfound.primefaces.model;

import javax.persistence.*;

@Entity
public class Client
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false, unique = true)
    private String numeroPiece;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(length = 9, nullable = false, unique = true)
    private String telephone;
    @Column(nullable = false)
    private int compte = 0;//La somme sur son compte
    private int argent_received = 0;//La somme qu'on lui a envoyer

    public Client()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getNumeroPiece()
    {
        return numeroPiece;
    }

    public void setNumeroPiece(String numeroPiece)
    {
        this.numeroPiece = numeroPiece;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public int getCompte()
    {
        return compte;
    }

    public void setCompte(int compte)
    {
        this.compte = compte;
    }

    public int getArgent_received()
    {
        return argent_received;
    }

    public void setArgent_received(int argent_received)
    {
        this.argent_received = argent_received;
    }
}