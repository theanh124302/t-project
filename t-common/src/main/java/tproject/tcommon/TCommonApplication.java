package tproject.tcommon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(TCommonApplication.class, args);
    }

}
