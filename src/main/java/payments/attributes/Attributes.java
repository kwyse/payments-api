package payments.attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import payments.attributes.parties.Parties;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonSerialize(using = AttributesSerializer.class)
public class Attributes {
    private Amount amount;
    private Parties parties;
    private References references;
    private Date processingDate;

    public Attributes() {
    }

    public Attributes(Amount amount, Parties parties, References references, Date processingDate) {
        this.amount = amount;
        this.parties = parties;
        this.references = references;
        this.processingDate = processingDate;
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

    public Date getProcessingDate() {
        return processingDate;
    }
}
