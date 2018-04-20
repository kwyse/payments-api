package payments;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Attributes {
    @Embedded
    private Amount amount;

    public Attributes() {
    }

    Attributes(Amount amount) {
        this.amount = amount;
    }

    public Amount getAmount() {
        return amount;
    }
}
