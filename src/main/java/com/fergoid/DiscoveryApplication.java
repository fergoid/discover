package com.fergoid;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableEurekaClient
public class DiscoveryApplication {

    public DiscoveryApplication() {
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryApplication.class)
                .web(false)
                .run(args);
    }



}

@Component
class DiscoveryClientExample implements CommandLineRunner {

    @Autowired
    private DiscoveryClient discoveryClient;

//    public DiscoveryClientExample() {
//    }

    @Override
    public void run(String... strings) throws Exception {
        discoverAllServices();
        discoverService("messageproducer");
        discoverService("dealing");


    }

    private void discoverAllServices() {
        System.out.println(discoveryClient.description());
        discoveryClient.getServices().forEach((String s) -> {
            discoverService(s);
        });
    }

    private void discoverService(String service) {
        System.out.println("discoverService: " + service);
        discoveryClient.getInstances(service).forEach((ServiceInstance s1) -> {
            System.out.println(service + " - " + s1.getUri());
            System.out.println(service + " - " + s1.getMetadata());
        });
    }
}

