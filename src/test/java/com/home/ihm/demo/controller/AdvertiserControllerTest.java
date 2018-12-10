package com.home.ihm.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.ihm.demo.domain.Advertiser;
import com.home.ihm.demo.dto.DebitCommand;
import com.home.ihm.demo.dto.DebitEvent;
import com.home.ihm.demo.service.AdvertiserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AdvertiserController.class, secure = false)
public class AdvertiserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    AdvertiserService service;


    @Test
    public void show() throws Exception {

        Advertiser advertiser = createAdvertiser();
        given(service.show(advertiser.getId())).willReturn(advertiser);

        mvc.perform(get("/api/advertiser/"+advertiser.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(advertiser.getName())));
    }

    @Test
    public void create() throws Exception {

        Advertiser advertiser = createAdvertiser();
        given(service.create(advertiser)).willReturn(advertiser);

        MvcResult result =
                mvc.perform(post("/api/advertiser")
                        .content(toJson(advertiser))
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andReturn();

        assertThat(result.getResponse().getContentAsString().contains("\"name:"+advertiser.getName()+"\""));
    }

    @Test
    public void update() throws Exception {

        Advertiser advertiser = createAdvertiser();
        given(service.update(advertiser.getId(), advertiser)).willReturn(advertiser);

        MvcResult result =
                mvc.perform(put("/api/advertiser/"+advertiser.getId())
                        .content(toJson(advertiser))
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        assertThat(result.getResponse().getContentAsString().contains("\"name:"+advertiser.getName()+"\""));
    }

    @Test
    public void remove() throws Exception {

        doNothing().when(service).delete(anyLong());

        mvc.perform(delete("/api/advertiser/"+anyLong())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("deleted", is(true)));
    }

    @Test
    public void validation() throws Exception {

        Advertiser advertiser = createAdvertiser();
        Long amount = 999L;
        given(service.isCreditWorthy(advertiser.getId(), amount)).willReturn(true);

        String uri = String.format("/api/advertiser/%d/validation?amount=%d", advertiser.getId(), amount);
        System.out.println("\nURI: " + uri);
        mvc.perform(get(uri)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("valid_credit", is(true)));
    }

    @Test
    public void showAll() throws Exception {

        Advertiser advertiser = createAdvertiser();
        given(service.showAll()).willReturn(Arrays.asList(advertiser));

        mvc.perform(get("/api/advertiser")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    public void debit() throws Exception {

        boolean succeeded = true;
        Advertiser advertiser = createAdvertiser();
        Long deduction = advertiser.getCreditLimt() - (advertiser.getCreditLimt() - 1);

        DebitCommand debitRequest = DebitCommand.builder().advertiserId(advertiser.getId()).amount(deduction).build();
        DebitEvent debitResponse = DebitEvent.builder().succeeded(succeeded).build();

        given(service.show(advertiser.getId())).willReturn(advertiser);
        given(service.deductCredit(advertiser.getId(), deduction)).willReturn(anyLong());

        mvc.perform(post("/api/advertiser/deductCredit")
                .content(new ObjectMapper().writeValueAsString(debitRequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("succeeded", is(debitResponse.isSucceeded())));
    }

    @Test
    public void debitOverLimit() throws Exception {

        boolean failed = false;
        Advertiser advertiser = createAdvertiser();
        Long deduction = advertiser.getCreditLimt() + 1;

        DebitCommand debitRequest = DebitCommand.builder().advertiserId(advertiser.getId()).amount(deduction).build();
        DebitEvent debitResponse = DebitEvent.builder().succeeded(failed).build();

        given(service.show(advertiser.getId())).willReturn(advertiser);
        doThrow(IllegalArgumentException.class).when(service).deductCredit(advertiser.getId(), deduction);

        mvc.perform(post("/api/advertiser/deductCredit")
                .content(new ObjectMapper().writeValueAsString(debitRequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("succeeded", is(debitResponse.isSucceeded())));
    }


    private Advertiser createAdvertiser() {
        Advertiser advertiser = new Advertiser();
        advertiser.setId(1L); // normally, exists after persisted only
        advertiser.setCreditLimt(1000L);
        advertiser.setName(String.format("Advertiser %d", 1));
        advertiser.setContactName(String.format("Advertiser Contact %d", 1));
        return advertiser;
    }

    private String toJson(Advertiser advertiser) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(advertiser);
    }
}