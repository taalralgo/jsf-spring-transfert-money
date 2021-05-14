package com.codenotfound.primefaces.controller;

import com.codenotfound.primefaces.config.Utils;
import com.codenotfound.primefaces.model.Client;
import com.codenotfound.primefaces.model.Role;
import com.codenotfound.primefaces.model.Utilisateur;
import com.codenotfound.primefaces.repository.ClientRepository;
import com.codenotfound.primefaces.repository.RoleRepository;
import com.codenotfound.primefaces.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@Controller
@Named
public class ClientController
{
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private ClientRepository clientRepository;
    private List<Client> clients;
    private Client client;
    private int compte;
    @Autowired
    private Utils utils;
    private Utilisateur connectedUser;

    @PostConstruct
    public void init()
    {
        this.initData();
    }

    private void initData()
    {
        client = new Client();
        compte = 0;
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
            if (client.getCompte() < 1000)
            {
                String errorMsg = "Le solde du client inferieur a 1000F!";
                FacesContext.getCurrentInstance().addMessage("compte", new FacesMessage(errorMsg));
                return "create";
            }
            Utilisateur connected = utilisateurRepository.findByLogin(utils.getConnectedUser());
            client.setAdminId(connected.getAdminId());
            clientRepository.save(client);
            initData();
            return "index?faces-redirect=true";
        }
        catch (Exception ex)
        {
            String errorMsg = "Une erreur est survenue";
            FacesContext.getCurrentInstance().addMessage("info", new FacesMessage(errorMsg));
            return "create";
        }
    }

    public String edit(Client client)
    {
        this.client = client;
        return "edit?faces-redirect=true";
    }

    public String editSolde(Client client)
    {
        this.client = client;
        return "edit-solde?faces-redirect=true";
    }

    public String update()
    {
        clientRepository.save(client);
        initData();
        return "index?faces-redirect=true";
    }

    public String updateSolde()
    {
        if (this.compte >= 1000)
        {
            this.client.setCompte(this.compte + this.client.getCompte());
            Utilisateur connected = getConnectedUser();
            connected.setIv(connected.getIv() - this.compte);
            utilisateurRepository.save(connected);
            clientRepository.save(this.client);
            initData();
            return "index?faces-redirect=true";
        }
        return "edit";
    }

    public String delete(Client client)
    {
        try
        {
            clientRepository.delete(client);
            initData();
            return "index?faces-redirect=true";
        }
        catch (Exception ex)
        {
            initData();
            return "index?faces-redirect=true";
        }
    }

    public List<Client> getClients()
    {
        Utilisateur u = utilisateurRepository.findByLogin(utils.getConnectedUser());
        clients = clientRepository.findAllByAdminId(u.getAdminId());
        return clients;
    }

    public void setClients(List<Client> clients)
    {
        this.clients = clients;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public int getCompte()
    {
        return compte;
    }

    public void setCompte(int compte)
    {
        this.compte = compte;
    }

    public Utilisateur getConnectedUser()
    {
        connectedUser = utilisateurRepository.findByLogin(utils.getConnectedUser());
        return connectedUser;
    }
}

