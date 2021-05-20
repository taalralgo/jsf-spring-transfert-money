package com.codenotfound.primefaces.repository;

import com.codenotfound.primefaces.model.Gain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GainRepository extends JpaRepository<Gain,Long>
{
}
