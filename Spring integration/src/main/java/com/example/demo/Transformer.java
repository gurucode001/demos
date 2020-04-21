package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class Transformer {
    public String transform(String filePath) throws IOException {
        System.out.println("2.0. ################################### transform start");
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return "transformed : "+content;
    }
}
