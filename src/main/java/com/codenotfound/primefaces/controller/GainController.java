package com.codenotfound.primefaces.controller;

import com.codenotfound.primefaces.config.Utils;
import com.codenotfound.primefaces.model.Client;
import com.codenotfound.primefaces.model.Gain;
import com.codenotfound.primefaces.model.Utilisateur;
import com.codenotfound.primefaces.repository.ClientRepository;
import com.codenotfound.primefaces.repository.GainRepository;
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
public class GainController
{
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private GainRepository gainRepository;
    private List<Gain> gains;
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
        gains = null;
    }

    public List<Gain> getGains()
    {
        gains = gainRepository.findAll();
        return gains;
    }

    public void setGains(List<Gain> gains)
    {
        this.gains = gains;
    }

    public Utilisateur getConnectedUser()
    {
        connectedUser = utilisateurRepository.findByLogin(utils.getConnectedUser());
        return connectedUser;
    }

    public String distribuer(Gain gain)
    {
        Utilisateur caissier1 = gain.getCaissier1();
        Utilisateur caissier2 = gain.getCaissier2();
        caissier1.setIv((int) (caissier1.getIv() + gain.getGainCaissier1()));
        caissier2.setIv((int) (caissier2.getIv() + gain.getGainCaissier2()));
        gain.setSolded(true);
        utilisateurRepository.save(caissier1);
        gainRepository.save(gain);
        utilisateurRepository.save(caissier2);
        return "index?faces-redirect=true";
    }
}

