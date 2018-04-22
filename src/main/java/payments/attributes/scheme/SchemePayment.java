package payments.attributes.scheme;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SchemePayment {
    @Column(name = "scheme_payment_type")
    private SchemePaymentType type;
    @Column(name = "scheme_payment_subtype")
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
