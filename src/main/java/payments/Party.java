package payments;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.Embeddable;

@Embeddable
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Party {
    private String name;

    public Party() {
    }

    public Party(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
