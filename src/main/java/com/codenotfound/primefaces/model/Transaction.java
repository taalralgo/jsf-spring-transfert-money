package com.codenotfound.primefaces.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String numeroPiece;
    @Column(nullable = false)
    private Long idReceiveClient; //ID du client qui recoit
    @Column(length = 255, nullable = false)
    private String code;
    @ManyToOne(fetch = FetchType.EAGER)
    private Utilisateur utilisateur;//Le cqissier qui a fait la transaction
    @Column(length = 100, nullable = false)
    private String numeroPieceEmetteur;
    @Column(nullable = false)
    private Long idEmetteur;
    private int montant;
    private Date createdAt = new Date();

    public Transaction()
    {
    }

    public Transaction(String numeroPiece, Long idReceiveClient, Utilisateur utilisateur, String numeroPieceEmetteur, Long idEmetteur, int montant, Date createdAt)
    {
        this.numeroPiece = numeroPiece;
        this.idReceiveClient = idReceiveClient;
        this.utilisateur = utilisateur;
        this.numeroPieceEmetteur = numeroPieceEmetteur;
        this.idEmetteur = idEmetteur;
        this.montant = montant;
        this.createdAt = createdAt;
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

    public Long getIdReceiveClient()
    {
        return idReceiveClient;
    }

    public void setIdReceiveClient(Long idReceiveClient)
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

    public Utilisateur getUtilisateur()
    {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur)
    {
        this.utilisateur = utilisateur;
    }

    public String getNumeroPieceEmetteur()
    {
        return numeroPieceEmetteur;
    }

    public void setNumeroPieceEmetteur(String numeroPieceEmetteur)
    {
        this.numeroPieceEmetteur = numeroPieceEmetteur;
    }

    public Long getIdEmetteur()
    {
        return idEmetteur;
    }

    public void setIdEmetteur(Long idEmetteur)
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
}

