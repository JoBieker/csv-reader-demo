package nrw.bieker.batch.csvreaderdemo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
@EqualsAndHashCode
public class InputZeile {

    private String name;
    private String age;
}
