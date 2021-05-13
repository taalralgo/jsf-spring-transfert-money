package com.codenotfound.primefaces.repository;

import com.codenotfound.primefaces.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client,Long>
{
    public List<Client> findAllByAdminId(int adminId);
    public Client findClientByNumeroPiece(String numpiece);
    public Client findClientById(Long id);
}
