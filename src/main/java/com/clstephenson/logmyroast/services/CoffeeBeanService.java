package com.clstephenson.logmyroast.services;

import com.clstephenson.logmyroast.models.CoffeeBean;

import java.util.List;
import java.util.Optional;

public interface CoffeeBeanService {

    List<CoffeeBean> findAllCoffeeBeans();

    Optional<CoffeeBean> findCoffeeBeanById(int id);

    CoffeeBean save(CoffeeBean bean);

    void deleteCoffeeBeanById(int id);
}
