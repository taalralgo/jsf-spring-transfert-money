package com.codenotfound.primefaces.controller;

import com.codenotfound.primefaces.config.Utils;
import com.codenotfound.primefaces.model.Client;
import com.codenotfound.primefaces.model.Role;
import com.codenotfound.primefaces.model.Transaction;
import com.codenotfound.primefaces.model.Utilisateur;
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
    private Utils utils;
    private Utilisateur connectedUser;
    private List<Transaction> transactions;
    private Transaction transaction;
    private Client emetteur;

    @PostConstruct
    public void init()
    {
        this.initData();
    }

    private void initData()
    {
        emetteur = new Client();
        transaction = new Transaction();
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
//            FacesContext.getCurrentInstance().addMessage(user.getRole().getLibRole(), new FacesMessage("Role Error"));
            return "create";
        }
        catch (Exception ex)
        {
//            String errorMsg = ex.getMessage();
//            FacesContext.getCurrentInstance().addMessage(user.getPwd(), new FacesMessage(errorMsg));
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

    public Utilisateur getConnectedUser()
    {
        connectedUser = utilisateurRepository.findByLogin(utils.getConnectedUser());
        return connectedUser;
    }

    public void setConnectedUser(Utilisateur connectedUser)
    {
        this.connectedUser = connectedUser;
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
            transactions = transactionRepository.findAllByUtilisateur(u);
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
}

