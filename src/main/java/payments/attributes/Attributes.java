package payments.attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import payments.attributes.amount.Amount;
import payments.attributes.amount.fx.ForeignExchange;
import payments.attributes.details.PaymentDetails;
import payments.attributes.parties.Parties;
import payments.attributes.scheme.SchemePayment;

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
    private SchemePayment schemePayment;

    @OneToOne
    private ForeignExchange foreignExchange;

    @OneToOne
    private Charges charges;

    public Attributes() {
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

    public ForeignExchange getForeignExchange() {
        return foreignExchange;
    }

    public SchemePayment getSchemePayment() {
        return schemePayment;
    }

    void setAmount(Amount amount) {
        this.amount = amount;
    }

    void setParties(Parties parties) {
        this.parties = parties;
    }

    void setReferences(References references) {
        this.references = references;
    }

    void setProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
    }

    void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    void setSchemePayment(SchemePayment schemePayment) {
        this.schemePayment = schemePayment;
    }

    void setForeignExchange(ForeignExchange foreignExchange) {
        this.foreignExchange = foreignExchange;
    }

    void setCharges(Charges charges) {
        this.charges = charges;
    }
}
