package org.tech.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tech.Exceptions.AccessDeniedException;
import org.tech.Models.Flea;
import org.tech.Services.Interfaces.IFleaService;


import java.util.List;
@RestController
@RequestMapping("/Flea")
public class FleaController {

    private final IFleaService service;
    @Autowired
    public FleaController(IFleaService service) {
        this.service = service;
    }


    @PostMapping("/create")
    public void create(@ModelAttribute("flea") Flea flea) {
        service.save(flea);
    }

    @GetMapping("/getById/{id}")
    public Flea getById(@RequestParam("id") Integer id) throws AccessDeniedException {
        return service.getById(id);
    }

    @GetMapping("/getAll")
    public List<Flea> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id) throws AccessDeniedException {
        service.deleteById(id);
    }

    @DeleteMapping("deleteAll")
    public void deleteAll() {
        service.deleteAll();
    }

    @GetMapping("/getAllByCatId")
    public List<Flea> getAllByCatId(Integer catId) {
        return service.getAllByCatId(catId);
    }

    @GetMapping("/getAllByName")
    public List<Flea> getAllByName(String name) {
        return service.getAllByName(name);
    }

}