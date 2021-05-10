package com.codenotfound.primefaces.repository;

import com.codenotfound.primefaces.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer>
{
    public Role findById(int id);
    public List<Role> findAllByLibRoleNotIn(List<String> list);
}
