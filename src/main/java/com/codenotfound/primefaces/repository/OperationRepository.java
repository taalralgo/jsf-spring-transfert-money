package com.codenotfound.primefaces.repository;

import com.codenotfound.primefaces.model.Operation;
import com.codenotfound.primefaces.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Utilisateur,Integer>
{
}
