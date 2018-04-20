package payments.attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import payments.attributes.parties.Parties;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonSerialize(using = AttributesSerializer.class)
public class Attributes {
    @Embedded
    private Amount amount;

    @Embedded
    private Parties parties;

    public Attributes() {
    }

    public Attributes(Amount amount, Parties parties) {
        this.amount = amount;
        this.parties = parties;
    }

    public Amount getAmount() {
        return amount;
    }

    public Parties getParties() {
        return parties;
    }
}