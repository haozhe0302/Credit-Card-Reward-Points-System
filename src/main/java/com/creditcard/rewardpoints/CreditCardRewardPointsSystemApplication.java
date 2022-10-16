package com.creditcard.rewardpoints;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class CreditCardRewardPointsSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditCardRewardPointsSystemApplication.class, args);
    }
}
