package nrw.bieker.batch.csvreaderdemo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Setter @Getter
public class OutputZeile {
    private String name;
    private Integer age;
    private UUID id;
}
