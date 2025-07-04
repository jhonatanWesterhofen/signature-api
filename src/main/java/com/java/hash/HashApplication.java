package com.java.hash;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Jhonatan
 */
@SpringBootApplication
public class HashApplication {

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        SpringApplication.run(HashApplication.class, args);
    }
}