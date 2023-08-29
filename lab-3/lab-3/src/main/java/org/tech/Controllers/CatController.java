package org.tech.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tech.Exceptions.AccessDeniedException;
import org.tech.Models.Cat;
import org.tech.Services.Interfaces.ICatService;
import org.tech.Services.OwnerService;
import org.tech.Services.UserService;

import java.util.List;

@RestController
@RequestMapping("/Cat")
public class CatController {
    private final ICatService service;
    private final OwnerService ownerService;
    private final UserService userService;

    @Autowired
    public CatController(ICatService service, OwnerService ownerService, UserService userService) {
        this.service = service;
        this.ownerService = ownerService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public void create(Cat cat) {
        service.save(cat);
    }

    @GetMapping("/getById/{id}")
    public Cat getById(@RequestParam("id") Integer id) throws AccessDeniedException {
        return service.getById(id);
    }

    @GetMapping("/getAll")
    public List<Cat> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id) throws AccessDeniedException {
        service.deleteById(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        service.deleteAll();
    }

    @GetMapping("/getAllByName")
    public List<Cat> getAllByName(String name) {
        return service.getAllByName(name);
    }

    @GetMapping("/getAllByOwnerId")
    public List<Cat> getAllByOwnerId(Integer ownerId) throws AccessDeniedException {
        return service.getAllByOwnerId(ownerId);
    }
}