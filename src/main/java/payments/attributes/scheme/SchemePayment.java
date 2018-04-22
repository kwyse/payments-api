package payments.attributes.scheme;

import javax.persistence.Embeddable;

@Embeddable
public class SchemePayment {
    private SchemePaymentType type;
    private SchemePaymentSubtype subtype;

    public SchemePayment() {
    }

    public SchemePayment(SchemePaymentType type, SchemePaymentSubtype subtype) {
        this.type = type;
        this.subtype = subtype;
    }

    public SchemePaymentType getType() {
        return type;
    }

    public SchemePaymentSubtype getSubtype() {
        return subtype;
    }
}
