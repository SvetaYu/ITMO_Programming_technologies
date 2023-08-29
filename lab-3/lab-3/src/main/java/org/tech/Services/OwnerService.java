package org.tech.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tech.Models.Owner;
import org.tech.Repositories.OwnerRepository;
import org.tech.Services.Interfaces.IOwnerService;

import java.util.List;

@Service
public class OwnerService implements IOwnerService {

    private OwnerRepository repository;
//    private UserRepository userRepo;

//    private HttpServletRequest requestHandlerServlet;

    @Autowired
    public OwnerService(OwnerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Owner save(Owner entity) {
        return repository.save(entity);
    }

    @Override
    public Owner getById(Integer id) {
//        var currentUserName = requestHandlerServlet.getUserPrincipal().getName();
//        var user = userRepo.getByName(currentUserName);
////        var owner = repository.getById(user.getOwnerId);//(
//        var cat = repository.findById(id).get();
//        cat.getOwnerId == user.getOwnerId;
        return repository.findById(id).get();
    }

    @Override
    public List<Owner> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByEntity(Owner entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<Owner> getAllByName(String name) {
        return repository.getAllByName(name);
    }

}
