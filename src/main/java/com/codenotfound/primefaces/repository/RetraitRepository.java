package com.codenotfound.primefaces.repository;

import com.codenotfound.primefaces.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RetraitRepository extends JpaRepository<Utilisateur,Integer>
{
    public Utilisateur findByLogin(String email);
    public Utilisateur findById(int id);
    public List<Utilisateur> findByAdminId(int id);
}
