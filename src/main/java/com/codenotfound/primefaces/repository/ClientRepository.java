package com.codenotfound.primefaces.repository;

import com.codenotfound.primefaces.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client,Long>
{
}
