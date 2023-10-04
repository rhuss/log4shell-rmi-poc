package com.log4j.vulnerabel.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class Log4jController {

    public static final Logger logger = LogManager.getLogger(Log4jController.class);

    private String contentType(String name) {
        String contenttype = "text/html; charset=utf-8";
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
            contenttype = "image/jpeg";
        } else if (name.endsWith(".png")) {
            contenttype = "image/png";
        } else if (name.endsWith(".txt")) {
            contenttype = "text/plain";
        } else if (name.endsWith(".js")) {
            contenttype = "text/javascript";
        } else if (name.endsWith(".htm") || name.endsWith(".html")) {
            contenttype = "text/html; charset=utf-8";
        } else if (name.endsWith(".gif")) {
            contenttype = "image/gif";
        } else if (name.endsWith(".css")) {
            contenttype = "text/css";
        } else if (name.endsWith(".bmp")) {
            contenttype = "image/bmp";
        }
        //logger.info("Content-Type: " + contenttype);
        return contenttype;
    }

    @GetMapping(value = "/hello")
    public String hello(@RequestHeader("User-agent") String useragent) {
        //logger.info("Received User-agent " + useragent);

        return "Hello\n";
    }

    @GetMapping(value = "/web/{p1}")
    public String web1(@PathVariable("p1") String p1, HttpServletResponse response) {
        //logger.info("/web/{p1} Received ");
        //logger.info("p1 = " + p1);
        try {
            Path path = FileSystems.getDefault().getPath("web", p1);
            //logger.info("Path = " + path.toString());

            String content = new String(Files.readAllBytes(path));
            response.addHeader("Content-Type", contentType(p1));
            return content;
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
            e.printStackTrace();
            return "";
        }
    }
    @GetMapping(value = "/web/{p1}/{p2}")
    public ResponseEntity<byte[]> web2(@PathVariable("p1") String p1, @PathVariable("p2") String p2) {
        //logger.info("/web/{p1}/{p2} Received " );
        //logger.info("p1 = " + p1);
        //logger.info("p2 = " + p2);
        try {
            Path path = FileSystems.getDefault().getPath("web", p1, p2);
            //logger.info("Path = " + path.toString());
            byte[] content = Files.readAllBytes(path);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", contentType(p2));

            return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(content);
            //response.addHeader("Content-Type", contentType(p2));
            //return content;
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
            e.printStackTrace();
            return ResponseEntity.ok().body("Error".getBytes());
        }
    }
    @GetMapping(value = "/web")
    public String web0(@RequestHeader("User-agent") String useragent, HttpServletRequest request, HttpServletResponse response) {
        //logger.info("/web Received!!  ");
        String ip = request.getRemoteAddr();
        logger.info("client ip is " + ip);
        if (ip.equals("127.0.0.1")) {
            logger.info("User-agent " + useragent);
        }
        try {
            Path path = FileSystems.getDefault().getPath("web", "index.html");
            //logger.info("Path = " + path.toString());
            String content = new String(Files.readAllBytes(path));
            response.addHeader("Content-Type", contentType("index.html"));
            return content;
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
            e.printStackTrace();
            return "";
        }
    }
}