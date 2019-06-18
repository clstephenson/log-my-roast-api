package com.clstephenson.logmyroast.services;

import com.clstephenson.logmyroast.models.CoffeeBean;
import com.clstephenson.logmyroast.models.Origin;
import com.clstephenson.logmyroast.repositories.CoffeeBeanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeBeanServiceImplTest {

    private static final Origin BEAN_1_ORIGIN = Origin.COLUMBIA;
    private static final String BEAN_1_FARM = "Los Hermanos";
    private static final Origin BEAN_2_ORIGIN = Origin.ETHIOPIA;
    private static final String BEAN_2_FARM = "Guji Majo";
    @Mock
    CoffeeBeanRepository coffeeBeanRepositoryMock;
    @InjectMocks
    CoffeeBeanServiceImpl coffeeBeanService;
    CoffeeBean bean1;
    CoffeeBean bean2;
    List<CoffeeBean> beans;

    @Before
    public void setup() {
        bean1 = new CoffeeBean(BEAN_1_ORIGIN, BEAN_1_FARM);
        bean2 = new CoffeeBean(BEAN_2_ORIGIN, BEAN_2_FARM);
        beans = Arrays.asList(bean1, bean2);
    }

    @Test
    public void findAllBeans_ReturnListOfBeans() {
        when(coffeeBeanRepositoryMock.findAll()).thenReturn(beans);
        assertThat(coffeeBeanService.findAllCoffeeBeans(), equalTo(beans));

        verify(coffeeBeanRepositoryMock, times(1)).findAll();
    }

    @Test
    public void findBeanById_ReturnBean() {
        when(coffeeBeanRepositoryMock.findById(anyInt())).thenReturn(Optional.of(bean1));
        assertThat(coffeeBeanService.findCoffeeBeanById(1).get(), equalTo(bean1));

        verify(coffeeBeanRepositoryMock, times(1)).findById(anyInt());
    }

    @Test
    public void save_ReturnSavedBean() {
        final Origin BEAN_3_ORIGIN = Origin.GUATEMALA;
        final String BEAN_3_FARM = "Farm in Guatemala";
        CoffeeBean bean3 = new CoffeeBean(BEAN_3_ORIGIN, BEAN_3_FARM);
        when(coffeeBeanRepositoryMock.save(bean3)).thenReturn(bean3);
        assertThat(coffeeBeanService.save(bean3), equalTo(bean3));

        verify(coffeeBeanRepositoryMock, times(1)).save(any(CoffeeBean.class));
    }

    @Test
    public void deleteById_repositoryMethodCalled() {
        doNothing().when(coffeeBeanRepositoryMock).deleteById(anyInt());
        coffeeBeanService.deleteCoffeeBeanById(1);
        verify(coffeeBeanRepositoryMock, times(1)).deleteById(anyInt());
    }
}