package payments.attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import payments.attributes.parties.Parties;

import javax.persistence.Embeddable;

@Embeddable
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonSerialize(using = AttributesSerializer.class)
public class Attributes {
    private Amount amount;
    private Parties parties;
    private References references;

    public Attributes() {
    }

    public Attributes(Amount amount, Parties parties, References references) {
        this.amount = amount;
        this.parties = parties;
        this.references = references;
    }

    public Amount getAmount() {
        return amount;
    }

    public Parties getParties() {
        return parties;
    }

    public References getReferences() {
        return references;
    }
}
