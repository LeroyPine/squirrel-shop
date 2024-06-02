package org.squirrel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luobaosong
 * @date 2024-02-01 17:46
 */
@RestController
@SpringBootApplication
public class SquirrelShopApplication {
    static Logger logger = LoggerFactory.getLogger(SquirrelShopApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SquirrelShopApplication.class, args);
        logger.info("Squirrel started!");
    }


}