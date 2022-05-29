package nrw.bieker.batch.csvreaderdemo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class CsvReaderDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsvReaderDemoApplication.class, args);
    }

}
