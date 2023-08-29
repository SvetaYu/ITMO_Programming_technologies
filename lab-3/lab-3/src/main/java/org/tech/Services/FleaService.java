package org.tech.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tech.Models.Flea;
import org.tech.Repositories.FleaRepository;
import org.tech.Services.Interfaces.IFleaService;


import java.util.List;

@Service
public class FleaService implements IFleaService {

    private FleaRepository repository;

    @Autowired
    public FleaService(FleaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flea save(Flea entity) {
        return repository.save(entity);
    }

    @Override
    public Flea getById(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Flea> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByEntity(Flea entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<Flea> getAllByCatId(Integer catId) {
        return repository.getAllByCatId(catId);
    }

    @Override
    public List<Flea> getAllByName(String name) {
        return repository.getAllByName(name);
    }
}
