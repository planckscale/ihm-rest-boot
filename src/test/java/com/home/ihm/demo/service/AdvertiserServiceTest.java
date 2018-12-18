package com.home.ihm.demo.service;

import com.home.ihm.demo.AdvertiserConfig;
import com.home.ihm.demo.ResourceNotFoundException;
import com.home.ihm.demo.domain.Advertiser;
import com.home.ihm.demo.repository.AdvertiserRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AdvertiserConfig.class }, loader = AnnotationConfigContextLoader.class)
public class AdvertiserServiceTest {

    @MockBean
    AdvertiserRepository repository;

    @Autowired
    AdvertiserService service;

    /* CRUD */

    @Test
    public void create() throws Exception {
        Advertiser advertiser =  Mockito.mock(Advertiser.class);
        service.create(advertiser);
        verify(repository, times(1)).save(advertiser);
    }

    @Test
    public void show() throws Exception {
        Advertiser advertiser =  Mockito.mock(Advertiser.class);
        when(repository.findById(advertiser.getId())).thenReturn(Optional.of(advertiser));
        service.show(advertiser.getId());
        verify(repository, times(1)).findById(advertiser.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void showShouldThrow() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        given(repository.findById(advertiser.getId())).willReturn(Optional.empty());
        service.show(advertiser.getId());
    }

    @Test
    public void update() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        given(repository.findById(advertiser.getId())).willReturn(Optional.of(advertiser));
        service.update(advertiser.getId(), advertiser);
        verify(repository, times(1)).save(advertiser);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateShouldThrow() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        given(repository.findById(advertiser.getId())).willReturn(Optional.empty());
        service.update(advertiser.getId(), advertiser);
    }

    @Test
    public void delete() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        service.delete(advertiser.getId());
        verify(repository, times(1)).deleteById(advertiser.getId());
    }

    @Test
    public void showAll() throws Exception {
        Advertiser advertiser =  Mockito.mock(Advertiser.class);
        when(repository.findAll()).thenReturn(Arrays.asList(advertiser));
        service.showAll();
        verify(repository, times(1)).findAll();
    }

    /* Logic */

    @Test
    public void isCreditWorthy() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        when(repository.findById(advertiser.getId())).thenReturn(Optional.of(advertiser));
        when(advertiser.getCreditLimt()).thenReturn(1000L);
        assertTrue(service.isCreditWorthy(advertiser.getId(), 999L));
    }

    @Test
    public void isNotCreditWorthy() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        when(repository.findById(advertiser.getId())).thenReturn(Optional.of(advertiser));
        when(advertiser.getCreditLimt()).thenReturn(1000L);
        assertFalse(service.isCreditWorthy(advertiser.getId(), 1001L));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void validationShouldThrow() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        given(repository.findById(advertiser.getId())).willReturn(Optional.empty());
        service.isCreditWorthy(advertiser.getId(), 999L);
    }

    @Test
    public void deductCredit() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        when(repository.findById(advertiser.getId())).thenReturn(Optional.of(advertiser));
        when(advertiser.getCreditLimt()).thenReturn(1000L);
        service.deductCredit(advertiser.getId(), 999L);
        verify(repository, times(1)).save(advertiser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deductCreditShouldThrow() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        when(repository.findById(advertiser.getId())).thenReturn(Optional.of(advertiser));
        when(advertiser.getCreditLimt()).thenReturn(1000L);
        service.deductCredit(advertiser.getId(), 1001L);
    }


}