package payments.attributes.parties;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class Parties {
    @OneToOne
    private Party beneficiary;
    @OneToOne
    private Party sponsor;

    public Parties() {
    }

    public Parties(Party beneficiary, Party sponsor) {
        this.beneficiary = beneficiary;
        this.sponsor = sponsor;
    }

    public Party getBeneficiary() {
        return this.beneficiary;
    }

    public Party getSponsor() {
        return this.sponsor;
    }
}
