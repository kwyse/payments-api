package payments.attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import payments.attributes.details.PaymentDetails;
import payments.attributes.parties.Parties;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.util.Date;

@Embeddable
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonSerialize(using = AttributesSerializer.class)
public class Attributes {
    private Amount amount;
    private Parties parties;
    private References references;
    private Date processingDate;
    private PaymentDetails paymentDetails;

    @OneToOne
    private Charges charges;

    public Attributes() {
    }

    public Attributes(Amount amount, Parties parties, References references, Date processingDate, PaymentDetails paymentDetails, Charges charges) {
        this.amount = amount;
        this.parties = parties;
        this.references = references;
        this.processingDate = processingDate;
        this.paymentDetails = paymentDetails;
        this.charges = charges;
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

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public Charges getCharges() {
        return charges;
    }
}
