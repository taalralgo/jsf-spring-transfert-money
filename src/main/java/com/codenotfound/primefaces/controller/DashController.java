package com.codenotfound.primefaces.controller;

import com.codenotfound.primefaces.config.Utils;
import com.codenotfound.primefaces.model.Client;
import com.codenotfound.primefaces.model.Transaction;
import com.codenotfound.primefaces.model.Utilisateur;
import com.codenotfound.primefaces.repository.ClientRepository;
import com.codenotfound.primefaces.repository.TransactionRepository;
import com.codenotfound.primefaces.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.List;

@ManagedBean
@Controller
@Named
public class DashController
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
    private List<Client> clients;
    private List<Utilisateur> admins;
    private List<Utilisateur> caissiers;
    private List<Transaction> transactions;
    private List<Transaction> retraits;
    private double sommes;
    private double pourcentNonRetirer;
    private double pourcentRetirer;

    @PostConstruct
    public void init()
    {
        this.initData();
    }

    private void initData()
    {
        sommes = 0;
        pourcentNonRetirer = 0;
        pourcentRetirer = 0;
    }

    public void index()
    {

    }

    public List<Client> getClients()
    {
        getConnectedUser();
        if(connectedUser.getRole().getLibRole().equals("ROLE_ADMIN"))
        {
            clients = clientRepository.findAllByAdminId(connectedUser.getId());
        }
        else if(connectedUser.getRole().getLibRole().equals("ROLE_CAISSIER"))
        {
            clients = clientRepository.findAllByAdminId(connectedUser.getAdminId());
        }
        else
        {
            clients = clientRepository.findAllByAdminId(connectedUser.getAdminId());
        }
        return clients;
    }

    public void setClients(List<Client> clients)
    {
        this.clients = clients;
    }

    public List<Utilisateur> getAdmins()
    {
        admins = utilisateurRepository.findAllByRole_LibRoleEquals("ROLE_ADMIN");
        return admins;
    }

    public void setAdmins(List<Utilisateur> admins)
    {
        this.admins = admins;
    }

    public List<Utilisateur> getCaissiers()
    {
        caissiers = utilisateurRepository.findAllByRole_LibRoleEquals("ROLE_CAISSIER");
        return caissiers;
    }

    public void setCaissiers(List<Utilisateur> caissiers)
    {
        this.caissiers = caissiers;
    }

    public List<Transaction> getTransactions()
    {
        getConnectedUser();
        if(connectedUser.getRole().getLibRole().equals("ROLE_ADMIN"))
        {
            transactions = transactionRepository.findAllByAdminId(connectedUser.getId());
        }
        else if(connectedUser.getRole().getLibRole().equals("ROLE_CAISSIER"))
        {
            transactions = transactionRepository.findAllByAdminId(connectedUser.getAdminId());
        }
        else
        {
            transactions = transactionRepository.findAllByIsRetirer(false);
        }
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    public List<Transaction> getRetraits()
    {
        transactions = transactionRepository.findAllByIsRetirer(true);
        return retraits;
    }

    public void setRetraits(List<Transaction> retraits)
    {
        this.retraits = retraits;
    }

    public double getSommes()
    {
        this.getTransactions();
        double val = 0;
        for (int i=0; i< transactions.size(); i++)
        {
            if(transactions.get(i) != null)
            {
                if(!transactions.get(i).getIsRetirer())
                {
                    val+=transactions.get(i).getMontant();
                }
            }
        }
        sommes = val;
        return sommes;
    }

    public void setSommes(double sommes)
    {
        this.sommes = sommes;
    }

    public double getPourcentNonRetirer()
    {
        return pourcentNonRetirer;
    }

    public void setPourcentNonRetirer(double pourcentNonRetirer)
    {
        this.pourcentNonRetirer = pourcentNonRetirer;
    }

    public double getPourcentRetirer()
    {
        List<Transaction> retirer = transactionRepository.findAllByIsRetirer(true);
        List<Transaction> nonretirer = transactionRepository.findAllByIsRetirer(false);
        double retirerVal = retirer.size();
        double nonretirerVal = nonretirer.size();
        try
        {
            pourcentRetirer = (retirerVal * 100)/nonretirerVal;
        }
        catch (Exception e)
        {
            pourcentRetirer = 0;
        }
        System.out.println("555555555555");
        System.out.println(retirerVal);
        System.out.println(nonretirerVal);
        System.out.println(pourcentRetirer);
        return pourcentRetirer;
    }

    public void setPourcentRetirer(double pourcentRetirer)
    {
        this.pourcentRetirer = pourcentRetirer;
    }

    public Utilisateur getConnectedUser()
    {
        connectedUser = utilisateurRepository.findByLogin(utils.getConnectedUser());
        return connectedUser;
    }
}

