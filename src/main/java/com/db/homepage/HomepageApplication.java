package com.db.homepage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.db.homepage.module.*.dao", "com.db.homepage.module.*.mapper"})
public class HomepageApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomepageApplication.class, args);
    }
}
