package com.codenotfound.primefaces.controller;

import com.codenotfound.primefaces.config.Utils;
import com.codenotfound.primefaces.model.Role;
import com.codenotfound.primefaces.model.Utilisateur;
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
public class UserController
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

    @GetMapping("/users")
    public String users()
    {
        users = utilisateurRepository.findAll();
//        return "redirect:/users/index.xhtml?faces-redirect=true";
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
        user.setRole(new Role());
    }

    private void resetData()
    {
        this.roleId = user.getRole().getId();
        this.pwdChanged = "";
    }

    @GetMapping("/user/create")
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
                System.out.println("password not valid");
                FacesContext.getCurrentInstance().addMessage(user.getPwd(), new FacesMessage("Le mot de passe n'est pas valide!"));
                return "create";
            }
            user.setPwd(bCryptPasswordEncoder.encode(pwdChanged));
            Role role = roleRepository.findById(roleId);
            if(role != null)
            {
                user.setCode(user.getLogin());
                user.setChanged(true);
                user.setRole(role);
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

    @PostMapping("/reset/password")
    public String resetPassword(@RequestParam(name = "password") String password,
                                @RequestParam(name = "newpassword") String newpassword,
                                @RequestParam(name = "confirm") String confirm,
                                Model model)
    {
//        String error = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur user = (Utilisateur) auth.getPrincipal();
        connectedUser = utilisateurRepository.findByLogin(user.getLogin());
        if (newpassword.length() < 6)
        {
            error = "Le mot de passe doit faire au moins 6 caracteres.";
            return "redirect:/reset.xhtml?error=true";
        }
        if (password.equals(newpassword))
        {
            error = "L'ancien mot de passe et le nouveau mot de passe doit etre different.";
            return "redirect:/reset.xhtml?error=true";
        }
        if (!newpassword.equals(confirm))
        {
            error = "La confirmation ne correspond pas au mot de passe.";
            return "redirect:/reset.xhtml?error=true";
        }

        connectedUser.setPwd(bCryptPasswordEncoder.encode(newpassword));
        connectedUser.setChanged(true);
        utilisateurRepository.save(connectedUser);
        return "redirect:/logout";
    }

    public String edit(Utilisateur user)
    {
        this.user = utilisateurRepository.findById(user.getId());
        this.roleId = this.user.getRole().getId();
        System.out.println("EDITTTTTTTTTTTTTTTTTTTTT");
        System.out.println(this.roleId);
        pwdChanged = "";
        return "edit?faces-redirect=true";
    }

    @PostMapping("user/edit")
    public String update()
    {
        System.out.println("UPPPPPPPPPPPp");
        Role r = roleRepository.findById(roleId);
        System.out.println("UPDATEEEEEEEEEEEEEEEEEE");
        System.out.println(r.getLibRole());
        if (r != null)
        {
            user.setRole(r);
            System.out.println("---------------------------------------");
            System.out.println(pwdChanged);
            System.out.println("---------------------------------------");
            utilisateurRepository.save(user);
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

//    public void getRoles()
//    {
//        System.out.println("----------------------------");
//        System.out.println(marqueId);
//        System.out.println("-----------------------------");
//        Marque m = marqueEJB.find(marqueId);
//        this.models = m.getModels();
////        this.models = marqueEJB.find(this.marqueId).getModels();
//    }

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
        users = utilisateurRepository.findAll();
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
}

