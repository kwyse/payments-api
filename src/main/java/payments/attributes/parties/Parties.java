package payments.attributes.parties;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class Parties {
    @OneToOne
    private Party beneficiary;
    @OneToOne
    private Party debtor;
    @OneToOne
    private Party sponsor;

    public Parties() {
    }

    public Parties(Party beneficiary, Party debtor, Party sponsor) {
        this.beneficiary = beneficiary;
        this.debtor = debtor;
        this.sponsor = sponsor;
    }

    public Party getBeneficiary() {
        return this.beneficiary;
    }

    public Party getDebtor() {
        return debtor;
    }

    public Party getSponsor() {
        return this.sponsor;
    }
}
