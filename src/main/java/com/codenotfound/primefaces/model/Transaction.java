package com.codenotfound.primefaces.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Client idReceiveClient; //ID du client qui recoit
    @Column(length = 255, nullable = false)
    private String code;//Code generer lors de l'envoi pour pouvoir recuperer
    @Column(length = 10, nullable = false)
    private String type; //transaction de type envoi ou retrait
    @ManyToOne(fetch = FetchType.EAGER)
    private Utilisateur utilisateur;
    @Column(nullable = false)
    private Client idEmetteur;
    private int montant;
    private int adminId;
    private Date createdAt = new Date();
    @Column(nullable = true)
    private Date dateTretrait = new Date();
    private boolean isRetirer = false;

    public Transaction()
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

    public Client getIdReceiveClient()
    {
        return idReceiveClient;
    }

    public void setIdReceiveClient(Client idReceiveClient)
    {
        this.idReceiveClient = idReceiveClient;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Utilisateur getUtilisateur()
    {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur)
    {
        this.utilisateur = utilisateur;
    }

    public Client getIdEmetteur()
    {
        return idEmetteur;
    }

    public void setIdEmetteur(Client idEmetteur)
    {
        this.idEmetteur = idEmetteur;
    }

    public int getMontant()
    {
        return montant;
    }

    public void setMontant(int montant)
    {
        this.montant = montant;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getDateTretrait()
    {
        return dateTretrait;
    }

    public void setDateTretrait(Date dateTretrait)
    {
        this.dateTretrait = dateTretrait;
    }

    public boolean isRetirer()
    {
        return isRetirer;
    }

    public void setRetirer(boolean retirer)
    {
        isRetirer = retirer;
    }
}

