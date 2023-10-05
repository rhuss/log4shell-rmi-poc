package knativecon2023.zerotrust;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class WebApp {

    public static final Logger logger = LogManager.getLogger(WebApp.class);

    // Demo handler, could be any handler that would log user provided input.
    @GetMapping(value = "/hello")
    public String hello(@RequestHeader("User-agent") String useragent) {
        // Innocent log statement that triggers the exploit
        logger.info("Received User-agent " + useragent);
        return "Hello\n";
    }

    public static void main(String[] args) {
		SpringApplication.run(WebApp.class, args);
	}

}
