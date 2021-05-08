package com.codenotfound.primefaces.controller;

import com.codenotfound.primefaces.model.Utilisateur;
import com.codenotfound.primefaces.repository.UtilisateurRepository;
import com.sun.faces.action.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController
{
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Utilisateur connectedUser;
    private List<Utilisateur> users;
    public String error = "";

    @GetMapping("/users")
    public String users()
    {
        users = utilisateurRepository.findAll();
        return "redirect:/users/index.xhtml";
    }

    @GetMapping("/user/create")
    public String create()
    {
        return "create";
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
        return users;
    }

    public void setUsers(List<Utilisateur> users)
    {
        this.users = users;
    }
}
