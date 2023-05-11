package ru.gis.gatewayproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class GatewayProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayProxyApplication.class, args);
    }

}
