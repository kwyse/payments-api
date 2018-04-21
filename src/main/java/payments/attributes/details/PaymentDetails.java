package payments.attributes.details;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PaymentDetails {
    @Column(name = "payment_id")
    private String id;
    @Column(name = "payment_purpose")
    private String purpose;
    @Column(name = "payment_scheme")
    private PaymentDetailsScheme scheme;
    @Column(name = "payment_type")
    private PaymentDetailsType type;

    public PaymentDetails() {
    }

    public PaymentDetails(String id, String purpose, PaymentDetailsScheme scheme, PaymentDetailsType type) {
        this.id = id;
        this.purpose = purpose;
        this.scheme = scheme;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getPurpose() {
        return purpose;
    }

    public PaymentDetailsScheme getScheme() {
        return scheme;
    }

    public PaymentDetailsType getType() {
        return type;
    }
}
