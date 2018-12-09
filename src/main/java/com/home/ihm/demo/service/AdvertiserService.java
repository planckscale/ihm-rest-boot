package com.home.ihm.demo.service;

import com.home.ihm.demo.domain.Advertiser;
import com.home.ihm.demo.repository.AdvertiserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AdvertiserService {

    @Autowired
    private AdvertiserRepository repository;


    @Transactional(readOnly = true)
    public Advertiser show(Long id) {
        return repository.getOne(id);
    }

    public Advertiser create(Advertiser advertiser) {
        return repository.save(advertiser);
    }

    public Advertiser update(Long id, Advertiser advertiser) {

        Advertiser existing = repository.getOne(id);

        existing.setName(advertiser.getName());
        existing.setContactName(advertiser.getContactName());
        existing.setCreditLimt(advertiser.getCreditLimt());

        return repository.save(advertiser);
    }

    public void delete(Long id) {
        repository.deleteById(id);
        return;
    }

    public boolean isCreditWorthy(Long advertiserId, Long debitAmount) {
        Advertiser advertiser = repository.getOne(advertiserId);
        return advertiser.getCreditLimt().compareTo(debitAmount) >= 0;
    }

}
