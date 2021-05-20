package com.codenotfound.primefaces.repository;

import com.codenotfound.primefaces.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer>
{
    public Utilisateur findByLogin(String email);
    public Utilisateur findById(int id);
    public Utilisateur findByNumeroPiece(String numeroPiece);
    public List<Utilisateur> findByAdminId(int id);
    public List<Utilisateur> findAllByRole_LibRoleEquals(String rolename);
}
