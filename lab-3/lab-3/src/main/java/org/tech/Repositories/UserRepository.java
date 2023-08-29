package org.tech.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tech.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getByUsername(String name);
}
