package org.tech.Services.Interfaces;

import org.tech.Models.Flea;

import java.util.List;

public interface IFleaService extends IService<Flea>{
    List<Flea> getAllByCatId(Integer catId);
    List<Flea> getAllByName(String name);
}
