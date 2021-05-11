package com.codenotfound.primefaces.controller;

import com.codenotfound.primefaces.config.Utils;
import com.codenotfound.primefaces.model.Role;
import com.codenotfound.primefaces.model.Utilisateur;
import com.codenotfound.primefaces.repository.RoleRepository;
import com.codenotfound.primefaces.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
public class CaissierController
{
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private Utils utils;
    private Utilisateur connectedUser;
    private List<Utilisateur> users;
    private String error = "";
    private Utilisateur user;
    private int roleId;
    private List<Role> roles;
    private String pwdChanged;

    @GetMapping("/caissiers")
    public String users()
    {
        Utilisateur user = utilisateurRepository.findByLogin(utils.getConnectedUser());
        users = utilisateurRepository.findByAdminId(user.getId());
        return "index.xhtml?faces-redirect=true";
    }

    @PostConstruct
    public void init()
    {
        this.initData();
    }

    private void initData()
    {
        this.roleId = 0;
        this.pwdChanged = "";
        user = new Utilisateur();
        user.setArticleContrat("Le lorem ipsum est, en imprimerie, une suite de mots sans signification utilisée à titre provisoire pour calibrer une mise en page, le texte définitif venant remplacer le faux-texte dès qu'il est prêt ou que la mise en page est achevée. Généralement, on utilise un texte en faux latin, le Lorem ipsum ou Lipsum.");
        user.setRole(new Role());
    }

    private void resetData()
    {
        this.roleId = user.getRole().getId();
        this.pwdChanged = "";
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
            if (pwdChanged.length() < 5)
            {
                String errorMsg = "Le mot de passe n'est pas valide!";
                FacesContext.getCurrentInstance().addMessage(user.getPwd(), new FacesMessage("Le mot de passe n'est pas valide!"));
                return "create";
            }
            user.setPwd(bCryptPasswordEncoder.encode(pwdChanged));
            Role role = roleRepository.findRoleByLibRole("ROLE_CAISSIER");
            if (role != null)
            {
                user.setCode(generateCode(user));
                user.setLogin(user.getCode());
                user.setChanged(false);
                user.setRole(role);
                user.setAdminId(utilisateurRepository.findByLogin(utils.getConnectedUser()).getId());
                utilisateurRepository.save(user);
                return "index?faces-redirect=true";
            }
            FacesContext.getCurrentInstance().addMessage(user.getRole().getLibRole(), new FacesMessage("Role Error"));
            return "create";
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(user.getPwd(), new FacesMessage(errorMsg));
            return "create";
        }
    }

    public String edit(Utilisateur user)
    {
        this.user = user;
        this.roleId = user.getRole().getId();
        pwdChanged = "";
        return "edit?faces-redirect=true";
    }

    public String update()
    {
        Role r = roleRepository.findRoleByLibRole("ROLE_CAISSIER");
        if (r != null)
        {
            user.setRole(r);
            if(!pwdChanged.equals(""))
            {
                user.setPwd(bCryptPasswordEncoder.encode(pwdChanged));
            }
            utilisateurRepository.save(user);
            init();
            initData();
            return "index?faces-redirect=true";
        }
        resetData();
        return "edit";
    }

    public String delete(Utilisateur user)
    {
        try
        {
            utilisateurRepository.delete(user);
            return "index?faces-redirect=true";
        }
        catch (Exception ex)
        {
            return "index?faces-redirect=true";
        }
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public List<Utilisateur> getUsers()
    {
        Utilisateur user = utilisateurRepository.findByLogin(utils.getConnectedUser());
        users = utilisateurRepository.findByAdminId(user.getId());
        return users;
    }

    public void setUsers(List<Utilisateur> users)
    {
        this.users = users;
    }

    public Utilisateur getUser()
    {
        return user;
    }

    public void setUser(Utilisateur user)
    {
        this.user = user;
    }

    public int getRoleId()
    {
        return roleId;
    }

    public void setRoleId(int roleId)
    {
        this.roleId = roleId;
    }

    public List<Role> getRoles()
    {
        roles = roleRepository.findAll();
        List<String> list = new ArrayList<String>();
        list.add("ROLE_CAISSIER");
        roles = roleRepository.findAllByLibRoleNotIn(list);
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    public String getPwdChanged()
    {
        return pwdChanged;
    }

    public void setPwdChanged(String pwdChanged)
    {
        this.pwdChanged = pwdChanged;
    }

    public String generateCode(Utilisateur utilisateur)
    {
        return utilisateur.getPrenom().toLowerCase().substring(0, 2)
                + '-'
                + utilisateur.getNom().toLowerCase().substring(0, 2)
                + '-'
                + utilisateur.getTelephone().toLowerCase().substring(0, 2)
                + '-'
                + utilisateur.getNumeroPiece().toLowerCase().substring(0, 4);
//        return codeGenerate;
    }
}

