package org.tech.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tech.Models.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
