package payments.attributes;

import payments.attributes.amount.Amount;
import payments.attributes.amount.fx.ForeignExchange;
import payments.attributes.details.PaymentDetails;
import payments.attributes.parties.Parties;
import payments.attributes.scheme.SchemePayment;

import java.util.Date;

public class AttributesBuilder {
    private Amount amount;
    private Parties parties;
    private References references;
    private Date processingDate;
    private PaymentDetails paymentDetails;
    private SchemePayment schemePayment;
    private ForeignExchange foreignExchange;
    private Charges charges;

    public AttributesBuilder() {
    }

    public AttributesBuilder withAmount(Amount amount) {
        this.amount = amount;
        return this;
    }

    public AttributesBuilder withParties(Parties parties) {
        this.parties = parties;
        return this;
    }

    public AttributesBuilder withReferences(References references) {
        this.references = references;
        return this;
    }

    public AttributesBuilder withProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
        return this;
    }

    public AttributesBuilder withPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
        return this;
    }

    public AttributesBuilder withSchemePayment(SchemePayment schemePayment) {
        this.schemePayment = schemePayment;
        return this;
    }

    public AttributesBuilder withForeignExchange(ForeignExchange foreignExchange) {
        this.foreignExchange = foreignExchange;
        return this;
    }

    public AttributesBuilder withCharges(Charges charges) {
        this.charges = charges;
        return this;
    }

    public Attributes build() {
        Attributes attributes = new Attributes();
        attributes.setAmount(this.amount);
        attributes.setCharges(this.charges);
        attributes.setForeignExchange(this.foreignExchange);
        attributes.setParties(this.parties);
        attributes.setPaymentDetails(this.paymentDetails);
        attributes.setProcessingDate(this.processingDate);
        attributes.setReferences(this.references);
        attributes.setSchemePayment(this.schemePayment);

        return attributes;
    }
}
