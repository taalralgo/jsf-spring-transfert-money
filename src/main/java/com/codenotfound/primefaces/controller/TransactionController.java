package com.codenotfound.primefaces.controller;

import com.codenotfound.primefaces.config.Utils;
import com.codenotfound.primefaces.model.Client;
import com.codenotfound.primefaces.model.Role;
import com.codenotfound.primefaces.model.Transaction;
import com.codenotfound.primefaces.model.Utilisateur;
import com.codenotfound.primefaces.repository.ClientRepository;
import com.codenotfound.primefaces.repository.RoleRepository;
import com.codenotfound.primefaces.repository.TransactionRepository;
import com.codenotfound.primefaces.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.List;

@ManagedBean
@Controller
@Named
public class TransactionController
{
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private Utils utils;
    private Utilisateur connectedUser;
    private List<Transaction> transactions;
    private Transaction transaction;
    private Client emetteur;
    private String transactionValidatorMsg;

    @PostConstruct
    public void init()
    {
        this.initData();
    }

    private void initData()
    {
        emetteur = new Client();
        transaction = new Transaction();
        transactionValidatorMsg = "";
    }

    public String create()
    {
        initData();
        return "create?faces-redirect=true";
    }

    public String store()
    {
        try
        {
            if (this.transactionValidator(this.transaction))
            {
                emetteur = clientRepository.findClientById(transaction.getIdEmetteur());
                if (emetteur != null)
                {
                    this.transaction.setCode(this.randomString(10));
                    this.transaction.setAdminId(getConnectedUser().getAdminId());
                    Utilisateur connected = getConnectedUser();
                    this.transaction.setUtilisateur(connected);
                    transactionRepository.save(this.transaction);
                    emetteur.setCompte(emetteur.getCompte() - transaction.getMontant());
                    clientRepository.save(emetteur);
//                    connected.setIv(connected.getIv() - this.transaction.getMontant());
                    initData();
                    return "index?faces-redirect=true";
                }
                FacesContext.getCurrentInstance().addMessage("info", new FacesMessage("Incoorect data"));
                return "create";
            }
            FacesContext.getCurrentInstance().addMessage("info", new FacesMessage(this.getTransactionValidatorMsg()));
            return "create";
        }
        catch (Exception ex)
        {
            String errorMsg = "Une erreur est survenue";
            FacesContext.getCurrentInstance().addMessage("info", new FacesMessage(errorMsg));
            return "create";
        }
    }

    public String edit(Transaction transaction)
    {
        this.transaction = transaction;
        return "edit?faces-redirect=true";
    }

    public String retirer(Transaction transaction)
    {
        this.transaction = transaction;
        return "retirer?faces-redirect=true";
    }

    public String update()
    {
        return "index?faces-redirect=true";
    }

    public String print(Transaction transaction)
    {
        try
        {
            transactionRepository.delete(transaction);
            return "index?faces-redirect=true";
        }
        catch (Exception ex)
        {
            return "index?faces-redirect=true";
        }
    }

    public String delete(Transaction transaction)
    {
        try
        {
            transactionRepository.delete(transaction);
            return "index?faces-redirect=true";
        }
        catch (Exception ex)
        {
            return "index?faces-redirect=true";
        }
    }

    public void getClientByNumpiece()
    {
        Client c = clientRepository.findClientByNumeroPiece(this.transaction.getEmetteurNumpiece());
        if (c != null)
        {
            this.transaction.setEmetteurNumpiece(c.getNumeroPiece());
            this.transaction.setIdEmetteur(c.getId());
            this.transaction.setEmetteurPrenom(c.getPrenom());
            this.transaction.setEmetteurNom(c.getNom());
            this.transaction.setEmetteurTelephone(c.getTelephone());
            this.transaction.setEmetteurSolde(c.getCompte());
        }
        else
        {
            this.transaction.setEmetteurNumpiece("");
            this.transaction.setEmetteurPrenom("");
            this.transaction.setEmetteurNom("");
            this.transaction.setEmetteurTelephone("");
        }
    }

    public boolean transactionValidator(Transaction transaction)
    {
        if (transaction.getEmetteurNumpiece().equals("") || transaction.getEmetteurNom().equals("") ||
                transaction.getEmetteurPrenom().equals("") ||
                transaction.getEmetteurTelephone().equals("") || transaction.getReceverNumpiece().equals("") ||
                transaction.getReceverPrenom().equals("") || transaction.getReceverNom().equals("") ||
                transaction.getReceverTelephone().equals(""))
            return false;
        else if (transaction.getEmetteurSolde() < transaction.getMontant())
            return false;
        return true;
    }

    public Utilisateur getConnectedUser()
    {
        connectedUser = utilisateurRepository.findByLogin(utils.getConnectedUser());
        return connectedUser;
    }

    public void setConnectedUser(Utilisateur connectedUser)
    {
        this.connectedUser = connectedUser;
    }

    public String randomString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++)
        {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString().toLowerCase();
    }

    public List<Transaction> getTransactions()
    {
        Utilisateur u = getConnectedUser();
        if (u.getRole().getLibRole().equals("ROLE_ADMIN"))
        {
            transactions = transactionRepository.findAllByAdminId(u.getId());
        }
        else
        {
//            transactions = transactionRepository.findAllByUtilisateur(u);
//            transactions = transactionRepository.findAllByAdminId(u.getAdminId());
            transactions = transactionRepository.findAll();
        }
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    public Transaction getTransaction()
    {
        return transaction;
    }

    public void setTransaction(Transaction transaction)
    {
        this.transaction = transaction;
    }

    public String getTransactionValidatorMsg()
    {
        if (transaction.getEmetteurNumpiece().equals("") || transaction.getEmetteurNom().equals("") ||
                transaction.getEmetteurPrenom().equals("") ||
                transaction.getEmetteurTelephone().equals("") || transaction.getReceverNumpiece().equals("") ||
                transaction.getReceverPrenom().equals("") || transaction.getReceverNom().equals("") ||
                transaction.getReceverTelephone().equals(""))
            this.setTransactionValidatorMsg("Un champs requis n'est pas renseigné");
        else if (transaction.getMontant() > transaction.getEmetteurSolde())
            this.setTransactionValidatorMsg("Le solde du client (" + transaction.getEmetteurSolde() + "FCFA) est insuffisant pour effectuer cette operation");
        return transactionValidatorMsg;
    }

    public void setTransactionValidatorMsg(String transactionValidatorMsg)
    {
        this.transactionValidatorMsg = transactionValidatorMsg;
    }
}

