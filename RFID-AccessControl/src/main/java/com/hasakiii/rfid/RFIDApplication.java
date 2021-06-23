package com.hasakiii.rfid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.hasakiii.rfid.mapper"})
public class RFIDApplication {

    public static void main(String[] args) {
        SpringApplication.run(RFIDApplication.class, args);
    }

}
