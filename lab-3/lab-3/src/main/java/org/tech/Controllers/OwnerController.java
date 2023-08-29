package org.tech.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tech.Exceptions.AccessDeniedException;
import org.tech.Models.Owner;
import org.tech.Services.Interfaces.IOwnerService;
import org.tech.Services.UserService;

import java.util.List;

@RestController
@RequestMapping("/Owner")
public class OwnerController {

    private final IOwnerService service;

    private final UserService userService;

    @Autowired
    public OwnerController(IOwnerService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/create")
    public void create(@ModelAttribute("Owner") Owner owner) {
//        var user = userService.getUserByName(ownerDto.username);
//        var owner = new Owner(ownerDto.name, ownerDto.dateOfBirth, user);
        service.save(owner);
    }

    @GetMapping("/getById/{id}")
    public Owner getById(@PathVariable("id") Integer id) throws AccessDeniedException {
        return service.getById(id);
    }

    @GetMapping("/getAll")
    public List<Owner> getAll() {
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
    public List<Owner> getAllByName(@ModelAttribute("name") String name) {
        return service.getAllByName(name);
    }

}