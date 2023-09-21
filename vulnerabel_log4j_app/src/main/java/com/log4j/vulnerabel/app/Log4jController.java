package com.log4j.vulnerabel.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class Log4jController {

    public static final Logger logger = LogManager.getLogger(Log4jController.class);


    @GetMapping(value = "/hello")
    public String hello(@RequestHeader("User-agent") String useragent) {
        logger.info("Received User-agent " + useragent);

        return "Hello\n";
    }

    @GetMapping(value = "/web/{p1}")
    public String web1(@PathVariable("p1") String p1, @RequestHeader("User-agent") String useragent) {
        logger.info("Received User-agent " + useragent);
        try {
            Path path = FileSystems.getDefault().getPath("web", p1);
            String content = new String(Files.readAllBytes(path));
            return content;
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
            e.printStackTrace();
            return "";
        }
    }
    @GetMapping(value = "/web/{p1}/{p2}")
    public String web2(@PathVariable("p1") String p1, @PathVariable("p2") String p2, @RequestHeader("User-agent") String useragent) {
        logger.info("Received User-agent " + useragent);
        try {
            Path path = FileSystems.getDefault().getPath("web", p1, p2);
            String content = new String(Files.readAllBytes(path));
            return content;
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
            e.printStackTrace();
            return "";
        }
    }
    @GetMapping(value = "/web")
    public String web0(@RequestHeader("User-agent") String useragent) {
        logger.info("Received User-agent " + useragent);
        try {
            Path path = FileSystems.getDefault().getPath("web", "index.html");
            String content = new String(Files.readAllBytes(path));
            return content;
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
            e.printStackTrace();
            return "";
        }
    }
}
