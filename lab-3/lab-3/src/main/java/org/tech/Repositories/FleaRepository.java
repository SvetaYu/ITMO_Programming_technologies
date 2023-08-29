package org.tech.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tech.Models.Flea;

import java.util.List;


@Repository
public interface FleaRepository extends JpaRepository<Flea, Integer> {
    List<Flea> getAllByCatId(Integer catId);
    List<Flea> getAllByName(String name);
}