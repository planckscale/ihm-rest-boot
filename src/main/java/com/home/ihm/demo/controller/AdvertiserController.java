package com.home.ihm.demo.controller;

import com.home.ihm.demo.domain.Advertiser;
import com.home.ihm.demo.service.AdvertiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/advertiser")
public class AdvertiserController {

    @Autowired
    private AdvertiserService advertiserService;


    // CRUD mappings TODO Global exception handling / response

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Advertiser create(@RequestBody Advertiser advertiser) {
        return advertiserService.create(advertiser);
    }

    @GetMapping("{id}")
    public Advertiser show(@PathVariable long id) {
        return advertiserService.show(id);
    }

    @PutMapping("{id}")
    public Advertiser update(@PathVariable long id, @RequestBody Advertiser advertiser) {
        return advertiserService.update(id, advertiser);
    }

    @DeleteMapping("{id}")
    public Map<String, Boolean> delete(@PathVariable long id) {
        advertiserService.delete(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    @GetMapping("{id}/validation")
    public Map<String, Boolean> validation(@PathVariable long id, @RequestParam Long amount) {
        boolean validCredit = advertiserService.isCreditWorthy(id, amount);
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid_credit", validCredit);
        return response;
    }

}