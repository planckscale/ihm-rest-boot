package com.home.ihm.demo.service;

import com.home.ihm.demo.AdvertiserConfig;
import com.home.ihm.demo.domain.Advertiser;
import com.home.ihm.demo.repository.AdvertiserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

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
        service.show(advertiser.getId());
        verify(repository, times(1)).getOne(advertiser.getId());
    }

    @Test
    public void update() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        given(repository.getOne(advertiser.getId())).willReturn(advertiser);
        service.update(advertiser.getId(), advertiser);
        verify(repository, times(1)).save(advertiser);
    }

    @Test
    public void delete() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        service.delete(advertiser.getId());
        verify(repository, times(1)).deleteById(advertiser.getId());
    }

    /* Logic */

    @Test
    public void isCreditWorthy() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        when(advertiser.getCreditLimt()).thenReturn(1000L);
        assertTrue(service.isCreditWorthy( advertiser, new BigDecimal(999) ));
    }

    @Test
    public void isNotCreditWorthy() throws Exception {
        Advertiser advertiser = Mockito.mock(Advertiser.class);
        when(advertiser.getCreditLimt()).thenReturn(1000L);
        assertFalse(service.isCreditWorthy( advertiser, new BigDecimal(1001) ));
    }


}