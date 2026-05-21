package org.three.dms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("org.three.dms.mapper")
public class dmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(dmsApplication.class, args);
    }
}
