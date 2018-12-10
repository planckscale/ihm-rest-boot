package com.home.ihm.demo.controller;

import com.home.ihm.demo.domain.Advertiser;
import com.home.ihm.demo.dto.DebitCommand;
import com.home.ihm.demo.dto.DebitEvent;
import com.home.ihm.demo.service.AdvertiserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/advertiser")
@Api(value="Advertiser Management API", description="Operations for Advertiser Management")
public class AdvertiserController {

    @Autowired
    private AdvertiserService advertiserService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create an advertiser", response = Advertiser.class)
    Advertiser create(@RequestBody Advertiser advertiser) {
        return advertiserService.create(advertiser);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "View an advertiser", response = Advertiser.class)
    public Advertiser show(@PathVariable long id) {
        return advertiserService.show(id);
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Update an advertiser", response = Advertiser.class)
    public Advertiser update(@PathVariable long id, @RequestBody Advertiser advertiser) {
        return advertiserService.update(id, advertiser);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete an advertiser", response = Map.class)
    public Map<String, Boolean> delete(@PathVariable long id) {
        advertiserService.delete(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    @GetMapping("{id}/validation")
    @ApiOperation(value = "Validate advertiser credit", response = Map.class)
    public Map<String, Boolean> validation(@PathVariable long id, @RequestParam Long amount) {
        boolean validCredit = advertiserService.isCreditWorthy(id, amount);
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid_credit", validCredit);
        return response;
    }

    @GetMapping
    @ApiOperation(value = "Get all advertisers", response = ArrayList.class)
    public List<Advertiser> all() {
        return advertiserService.showAll();
    }

    @PostMapping("/debit")
    @ApiOperation(value = "Debit with validation", response = DebitEvent.class)
    DebitEvent debit(@RequestBody DebitCommand debit) {

        boolean creditWorthy = advertiserService.isCreditWorthy(debit.getAdvertiserId(), debit.getAmount());
        String message = creditWorthy ? "Debit of %d succeeded" : "Debit of %d exceeds credit limit";

        return DebitEvent.builder()
                .succeeded(creditWorthy ? true : false)
                .message(String.format(message, debit.getAmount()))
                .build();
    }


    @PostConstruct
    // TODO delete me; demo sample data (other ways to do, just convenient)
    private void insertDemoData() {
        IntStream.range(1, 10).forEach(
                i -> {
                    Advertiser advertiser = new Advertiser();
                    advertiser.setName(String.format("Advertiser %s", String.valueOf(i)));
                    advertiser.setContactName(String.format("Advertiser Contact %s", String.valueOf(i)));
                    advertiser.setCreditLimt(10000L);
                    advertiserService.create(advertiser);
                }
        );
    }

}