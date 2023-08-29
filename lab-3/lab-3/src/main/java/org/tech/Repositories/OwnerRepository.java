package org.tech.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tech.Models.Owner;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,Integer> {
    List<Owner> getAllByName(String name);
}