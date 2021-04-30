package com.codenotfound.primefaces.repository;

import com.codenotfound.primefaces.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer>
{
    public Utilisateur findByLogin(String email);
}
