package org.tech.Services.Interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.tech.Models.Owner;

import java.util.List;

public interface IOwnerService extends IService<Owner> {
    List<Owner> getAllByName(String name);

}
