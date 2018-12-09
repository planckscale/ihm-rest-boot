package com.home.ihm.demo.service;

import com.home.ihm.demo.domain.Advertiser;
import com.home.ihm.demo.repository.AdvertiserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdvertiserService {

    @Autowired
    private AdvertiserRepository repository;


    @Transactional(readOnly = true)
    public Advertiser show(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Advertiser not found for this id: " + id));
    }

    public Advertiser create(Advertiser advertiser) {
        return repository.save(advertiser);
    }

    public Advertiser update(Long id, Advertiser advertiser) {

        Advertiser existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Advertiser not found for this id: " + id));

        existing.setName(advertiser.getName());
        existing.setContactName(advertiser.getContactName());
        existing.setCreditLimt(advertiser.getCreditLimt());

        return repository.save(advertiser);
    }

    public void delete(Long id) {
        repository.deleteById(id);
        return;
    }

    @Transactional(readOnly = true)
    public List<Advertiser> showAll() {
        return repository.findAll();
    }

    public boolean isCreditWorthy(Long advertiserId, Long debitAmount) {
        Advertiser advertiser = repository.findById(advertiserId).orElseThrow(() -> new RuntimeException("Advertiser not found for this id: " + advertiserId));
        return advertiser.getCreditLimt().compareTo(debitAmount) >= 0;
    }

}
