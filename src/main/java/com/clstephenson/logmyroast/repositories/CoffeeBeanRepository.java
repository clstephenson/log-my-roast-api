package com.clstephenson.logmyroast.repositories;

import com.clstephenson.logmyroast.models.CoffeeBean;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeBeanRepository extends CrudRepository<CoffeeBean, Integer> {
}
