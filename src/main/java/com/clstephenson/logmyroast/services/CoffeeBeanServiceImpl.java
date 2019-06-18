package com.clstephenson.logmyroast.services;

import com.clstephenson.logmyroast.models.CoffeeBean;
import com.clstephenson.logmyroast.repositories.CoffeeBeanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "CoffeeBeanService")
public class CoffeeBeanServiceImpl implements CoffeeBeanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeBeanServiceImpl.class);

    private CoffeeBeanRepository coffeeBeanRepository;

    public CoffeeBeanServiceImpl(CoffeeBeanRepository coffeeBeanRepository) {
        assert coffeeBeanRepository != null;
        this.coffeeBeanRepository = coffeeBeanRepository;
    }

    @Override
    public List<CoffeeBean> findAllCoffeeBeans() {
        return StreamSupport.stream(coffeeBeanRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CoffeeBean> findCoffeeBeanById(int id) {
        return coffeeBeanRepository.findById(id);
    }

    @Override
    public CoffeeBean save(CoffeeBean bean) {
        return coffeeBeanRepository.save(bean);
    }

    @Override
    public void deleteCoffeeBeanById(int id) {
        coffeeBeanRepository.deleteById(id);
    }
}
