package com.home.ihm.demo.actuator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Java6Assertions.entry;
import static org.assertj.core.api.Java6BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.server.port=0"})
// sanity check Actuator
public class ActuatorIntegrationTest {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldBeHealthStatusUp() throws Exception {
        /*
        curl http://localhost:8080/actuator/health
        {"status":"UP"}
         */
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.restTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/actuator/health", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains(entry("status", "UP"));
    }

}
