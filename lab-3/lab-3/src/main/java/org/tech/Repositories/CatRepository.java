package org.tech.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tech.Models.Cat;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Integer> {
    List<Cat> getAllByOwnerId(Integer ownerId);
    List<Cat> getAllByName(String name);
    void deleteAllByOwnerId(Integer ownerId);
}