package com.codenotfound.primefaces.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 255, nullable = false)
    private String code;//Code generer lors de l'envoi pour pouvoir recuperer
    @Column(length = 10, nullable = false)
    private String type; //transaction de type envoi ou retrait
    @ManyToOne(fetch = FetchType.EAGER)
    private Utilisateur utilisateur;//Le caissier qui a effectuer la transaction
    @Column(nullable = false)
    private Long idEmetteur;//Id du client qui effectue la transaction
    private String emetteurNumpiece;
    private String emetteurPrenom;
    private String emetteurNom;
    private String emetteurTelephone;

//    private Long receverId;
    private String receverNumpiece;
    private String receverPrenom;
    private String receverNom;
    private String receverTelephone;

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

    public Long getIdEmetteur()
    {
        return idEmetteur;
    }

    public void setIdEmetteur(Long idEmetteur)
    {
        this.idEmetteur = idEmetteur;
    }

    public String getEmetteurNumpiece()
    {
        return emetteurNumpiece;
    }

    public void setEmetteurNumpiece(String emetteurNumpiece)
    {
        this.emetteurNumpiece = emetteurNumpiece;
    }

    public String getEmetteurPrenom()
    {
        return emetteurPrenom;
    }

    public void setEmetteurPrenom(String emetteurPrenom)
    {
        this.emetteurPrenom = emetteurPrenom;
    }

    public String getEmetteurNom()
    {
        return emetteurNom;
    }

    public void setEmetteurNom(String emetteurNom)
    {
        this.emetteurNom = emetteurNom;
    }

    public String getEmetteurTelephone()
    {
        return emetteurTelephone;
    }

    public void setEmetteurTelephone(String emetteurTelephone)
    {
        this.emetteurTelephone = emetteurTelephone;
    }

    public String getReceverNumpiece()
    {
        return receverNumpiece;
    }

    public void setReceverNumpiece(String receverNumpiece)
    {
        this.receverNumpiece = receverNumpiece;
    }

    public String getReceverPrenom()
    {
        return receverPrenom;
    }

    public void setReceverPrenom(String receverPrenom)
    {
        this.receverPrenom = receverPrenom;
    }

    public String getReceverNom()
    {
        return receverNom;
    }

    public void setReceverNom(String receverNom)
    {
        this.receverNom = receverNom;
    }

    public String getReceverTelephone()
    {
        return receverTelephone;
    }

    public void setReceverTelephone(String receverTelephone)
    {
        this.receverTelephone = receverTelephone;
    }

    public int getMontant()
    {
        return montant;
    }

    public void setMontant(int montant)
    {
        this.montant = montant;
    }

    public int getAdminId()
    {
        return adminId;
    }

    public void setAdminId(int adminId)
    {
        this.adminId = adminId;
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

