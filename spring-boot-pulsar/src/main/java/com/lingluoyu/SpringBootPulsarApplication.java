package com.lingluoyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.pulsar.annotation.EnablePulsar;

@EnablePulsar
@SpringBootApplication
public class SpringBootPulsarApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootPulsarApplication.class, args);
    }

}
