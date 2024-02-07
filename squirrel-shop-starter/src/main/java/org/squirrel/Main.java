package org.squirrel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luobaosong
 * @date 2024-02-01 17:46
 */
@RestController
@SpringBootApplication
public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        logger.info("Squirrel started!");
    }


    @GetMapping("/hello")
    public String hello() {
        logger.info("hello info!");
        logger.warn("hello warn!");
        logger.error("hello error!");
        return "hello squirrel";
    }
}