package payments.attributes.parties;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Parties {
    @Embedded
    private Party beneficiary;

    public Parties() {
    }

    public Parties(Party beneficiary) {
        this.beneficiary = beneficiary;
    }

    public Party getBeneficiary() {
        return beneficiary;
    }
}
