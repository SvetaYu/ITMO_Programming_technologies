package org.tech.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tech.Exceptions.AccessDeniedException;
import org.tech.Models.Cat;
import org.tech.Repositories.CatRepository;
import org.tech.Repositories.UserRepository;
import org.tech.Services.Interfaces.ICatService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CatService implements ICatService {

    private CatRepository repository;

    private UserRepository userRepository;

    private HttpServletRequest request;


    @Autowired
    public CatService(CatRepository repository, UserRepository userRepository, HttpServletRequest request) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.request = request;
    }

    @Override
    public Cat save(Cat entity) {
        return repository.save(entity);
    }

    @Override
    public Cat getById(Integer id) throws AccessDeniedException {
        if (!request.isUserInRole("ADMIN")) {
            var owner = userRepository.getByUsername(request.getUserPrincipal().getName()).getOwner();
            var cat = repository.findById(id).get();
            if (cat.getOwner().getId() != owner.getId())
                throw new AccessDeniedException();
        }
        return repository.findById(id).get();
    }

    @Override
    public List<Cat> getAll() {
        var cats = repository.findAll();
        if (!request.isUserInRole("ADMIN")) {
            var owner = userRepository.getByUsername(request.getUserPrincipal().getName()).getOwner();
            cats = cats.stream().filter(cat -> owner.getId() == cat.getOwner().getId()).toList();
        }
        return cats;
    }

    @Override
    public void deleteById(Integer id) throws AccessDeniedException {
        getById(id);
        repository.deleteById(id);
    }

    @Override
    public void deleteByEntity(Cat entity) throws AccessDeniedException {
        if (!request.isUserInRole("ADMIN")) {
            var owner = userRepository.getByUsername(request.getUserPrincipal().getName()).getOwner();
            if (entity.getOwner().getId() != owner.getId())
                throw new AccessDeniedException();
        }
        repository.deleteById(entity.getId());
    }

    @Override
    public void deleteAll() {
        if (!request.isUserInRole("ADMIN")) {
            var owner = userRepository.getByUsername(request.getUserPrincipal().getName()).getOwner();
            repository.deleteAllByOwnerId(owner.getId());
        } else
            repository.deleteAll();
    }

    @Override
    public List<Cat> getAllByOwnerId(Integer ownerId) throws AccessDeniedException {
        if (!request.isUserInRole("ADMIN")) {
            var owner = userRepository.getByUsername(request.getUserPrincipal().getName()).getOwner();
            if (owner.getId() != ownerId)
                throw new AccessDeniedException();
        }
        return repository.getAllByOwnerId(ownerId);
    }

    @Override
    public List<Cat> getAllByName(String name) {
        var cats = repository.getAllByName(name);
        if (!request.isUserInRole("ADMIN")) {
            var owner = userRepository.getByUsername(request.getUserPrincipal().getName()).getOwner();
            cats = cats.stream().filter(cat -> owner.getId() == cat.getOwner().getId()).toList();
        }
        return cats;
    }
}
