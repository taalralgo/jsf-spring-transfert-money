package com.codenotfound.primefaces.repository;

import com.codenotfound.primefaces.model.Transaction;
import com.codenotfound.primefaces.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction,Long>
{
    public List<Transaction> findAllByUtilisateur(Utilisateur utilisateur);
    public List<Transaction> findAllByUtilisateurAndIsRetirerOrderByCreatedAtDesc(Utilisateur utilisateur, boolean isretirer);
    public List<Transaction> findAllByAdminId(int adminId);
    public List<Transaction> findTransactionsByCode(String code);
    public List<Transaction> findAllByIsRetirer(boolean isretirer);
}
