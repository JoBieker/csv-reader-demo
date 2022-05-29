package nrw.bieker.batch.csvreaderdemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nrw.bieker.batch.csvreaderdemo.model.InputZeile;
import nrw.bieker.batch.csvreaderdemo.model.OutputZeile;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.format.datetime.DateFormatter;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Configuration
@RequiredArgsConstructor
@Slf4j
public class CsvReaderDemoConfig {

    @Value("${nrw.bieker.batch.inputFields}")
    private String inputFieldNames;

    @Value("${nrw.bieker.batch.outputFields}")
    private String outputFieldNames;


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job getJob(){
        return jobBuilderFactory.get("csv-demo-job")
                .start(getStep())
                .build();
    }

    @Bean
    public Step getStep(){
        return stepBuilderFactory.get("csv-demo-step")
                .<InputZeile, OutputZeile>chunk(100)
                .reader(multiResourceItemReader(null))
                .processor(getProcessor())
                .writer(getWriter(null))
                .build();
    }

    @Bean
    @StepScope
    public MultiResourceItemReader multiResourceItemReader(
            @Value("${nrw.bieker.batch.inputResource}") Resource[] resources){
        return new MultiResourceItemReaderBuilder<InputZeile>()
                .name("csv-demo-reader")
                .resources(resources)
                .delegate(getReader())
                .build();

    }
    @Bean
    public FlatFileItemReader<InputZeile> getReader() {

        return new FlatFileItemReaderBuilder<InputZeile>()
                .name("csv-demo-flat-reader")
                .linesToSkip(1)
                .delimited().delimiter(";")
                .names(inputFieldNames.split(","))
                .targetType(InputZeile.class)
                .encoding("utf-8")
                .build();
    }

    private ItemProcessor<InputZeile,OutputZeile> getProcessor() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return inputZeile -> {
            OutputZeile outputZeile = new OutputZeile();
            outputZeile.setName(inputZeile.getName().toUpperCase(Locale.GERMANY));
            outputZeile.setAge(Integer.valueOf(inputZeile.getAge()));
            outputZeile.setId(UUID.randomUUID());
            return outputZeile;
        };
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<OutputZeile> getWriter(@Value("${nrw.bieker.batch.outputResource}") Resource resource) {
        return new FlatFileItemWriterBuilder<OutputZeile>()
                .name("csv-demo-writer")
                .resource(resource)
                .headerCallback(new FlatFileHeaderCallback() {
                    @Override
                    public void writeHeader(Writer writer) throws IOException {
                        writer.write(Arrays.stream(outputFieldNames.split(",")).collect(Collectors.joining(";")));
                    }
                })
                .encoding("utf-8")
                .shouldDeleteIfExists(true)
                .formatted().format("%s;%s;%s")
                .names(outputFieldNames.split(","))
                .build();
    }
}
