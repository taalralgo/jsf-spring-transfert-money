package com.codenotfound.primefaces.controller;

import com.codenotfound.primefaces.model.Utilisateur;
import com.codenotfound.primefaces.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class UserCrudView
{
    private List<Utilisateur> users;

    private Utilisateur selectedUser;

    private List<Utilisateur> selectedUsers;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostConstruct
    public void init() {
        this.users = this.utilisateurRepository.findAll();
    }

    public List<Utilisateur> getusers() {
        return users;
    }

    public Utilisateur getselectedUser() {
        return selectedUser;
    }

    public void setselectedUser(Utilisateur selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<Utilisateur> getselectedUsers() {
        return selectedUsers;
    }

    public void setselectedUsers(List<Utilisateur> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public void openNew() {
        this.selectedUser = new Utilisateur();
    }

    public void saveProduct() {
//        if (this.selectedUser.getCode() == null) {
//            this.selectedUser.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
//            this.users.add(this.selectedUser);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Added"));
//        }
//        else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Updated"));
//        }
//
//        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
//        PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
    }

    public void deleteProduct() {
//        this.users.remove(this.selectedUser);
//        this.selectedUser = null;
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Removed"));
//        PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
    }

    public String getDeleteButtonMessage() {
        if (hasselectedUsers()) {
            int size = this.selectedUsers.size();
            return size > 1 ? size + " users selected" : "1 product selected";
        }

        return "Delete";
    }

    public boolean hasselectedUsers() {
        return this.selectedUsers != null && !this.selectedUsers.isEmpty();
    }

    public void deleteselectedUsers() {
//        this.users.removeAll(this.selectedUsers);
//        this.selectedUsers = null;
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("users Removed"));
//        PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
//        PrimeFaces.current().executeScript("PF('dtusers').clearFilters()");
    }

    public List<Utilisateur> getUsers()
    {
        return users;
    }

    public void setUsers(List<Utilisateur> users)
    {
        this.users = users;
    }

    public Utilisateur getSelectedUser()
    {
        return selectedUser;
    }

    public void setSelectedUser(Utilisateur selectedUser)
    {
        this.selectedUser = selectedUser;
    }

    public List<Utilisateur> getSelectedUsers()
    {
        return selectedUsers;
    }

    public void setSelectedUsers(List<Utilisateur> selectedUsers)
    {
        this.selectedUsers = selectedUsers;
    }
}
