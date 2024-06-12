package org.squirrel;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author luobaosong
 * @date 2024-06-11 17:19
 */
@Slf4j
@SpringBootTest(
        classes = SquirrelShopApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
public class SquirrelShipApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void contextLoads() {
        JsonNode jsonNode = restTemplate.postForObject("http://localhost:8099/squ/ts", new HashMap<>(), JsonNode.class);
        assert jsonNode != null;
        log.info(jsonNode.toString());
    }

}
