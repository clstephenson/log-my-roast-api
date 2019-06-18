package com.clstephenson.logmyroast.controllers;

import com.clstephenson.logmyroast.exceptions.CoffeeBeanNotFoundException;
import com.clstephenson.logmyroast.models.CoffeeBean;
import com.clstephenson.logmyroast.services.CoffeeBeanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CoffeeBeanRestController {

    private CoffeeBeanService coffeeBeanService;

    public CoffeeBeanRestController(CoffeeBeanService coffeeBeanService) {
        assert coffeeBeanService != null;
        this.coffeeBeanService = coffeeBeanService;
    }

    @GetMapping(value = "/coffeebeans", produces = "application/json")
    public List<CoffeeBean> getCoffeeBeans() {
        return coffeeBeanService.findAllCoffeeBeans();
    }

    @GetMapping(value = "/coffeebeans/{id}", produces = "application/json")
    public CoffeeBean getCoffeeBean(@PathVariable int id) {
        return coffeeBeanService.findCoffeeBeanById(id)
                .orElseThrow(() -> new CoffeeBeanNotFoundException(id));
    }

    @PostMapping(value = "/coffeebeans", produces = "application/json")
    public ResponseEntity<CoffeeBean> createCoffeeBean(@RequestBody CoffeeBean coffeeBeanInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(coffeeBeanService.save(coffeeBeanInput));
    }

    @PutMapping(value = "/coffeebeans/{id}", produces = "application/json")
    public ResponseEntity<CoffeeBean> updateCoffeeBean(@PathVariable int id, @RequestBody CoffeeBean coffeeBeanInput) {
        if (coffeeBeanService.findCoffeeBeanById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(coffeeBeanService.save(coffeeBeanInput));
        } else {
            throw new CoffeeBeanNotFoundException(id);
        }
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/coffeebeans/{id}")
    public void deleteCoffeeBean(@PathVariable int id) {
        if (coffeeBeanService.findCoffeeBeanById(id).isPresent()) {
            coffeeBeanService.deleteCoffeeBeanById(id);
        } else {
            throw new CoffeeBeanNotFoundException(id);
        }
    }
}
