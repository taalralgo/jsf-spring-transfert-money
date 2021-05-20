package com.codenotfound.primefaces.controller;

import com.codenotfound.primefaces.config.Utils;
import com.codenotfound.primefaces.model.Client;
import com.codenotfound.primefaces.model.Gain;
import com.codenotfound.primefaces.model.Transaction;
import com.codenotfound.primefaces.model.Utilisateur;
import com.codenotfound.primefaces.repository.ClientRepository;
import com.codenotfound.primefaces.repository.GainRepository;
import com.codenotfound.primefaces.repository.TransactionRepository;
import com.codenotfound.primefaces.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.Date;
import java.util.List;

@ManagedBean
@Controller
@Named
public class OperationController
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

    @PostConstruct
    public void init()
    {
        this.initData();
    }

    private void initData()
    {
        transaction = new Transaction();
        transactions = null;
    }

    public List<Transaction> getTransactions()
    {
        initData();
        Utilisateur connected = getConnectedUser();
        if(connected.getRole().getLibRole().equals("ROLE_ADMIN"))
        {
            transactions = transactionRepository.findAllByAdminId(connected.getId());
        }
        else
        {
            transactions = transactionRepository.findAllByUtilisateurAndIsRetirerOrderByCreatedAtDesc(connected, false);
        }
        return transactions;
    }

    public String envoiePrint(Transaction transaction)
    {
        this.transaction = transaction;
        return "envoie-print?faces-redirect=true";
    }

    public void setTransactions(List<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    public Utilisateur getConnectedUser()
    {
        connectedUser = utilisateurRepository.findByLogin(utils.getConnectedUser());
        return connectedUser;
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

