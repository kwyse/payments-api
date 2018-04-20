package payments;

import javax.persistence.Embeddable;
import java.math.BigInteger;

@Embeddable
public class Amount {
    private BigInteger majorUnits;
    private int minorUnits;
    private Currency currency;

    public Amount() {
    }

    Amount(BigInteger majorUnits, int minorUnits, Currency currency) {
        this.majorUnits = majorUnits;
        this.minorUnits = minorUnits;
        this.currency = currency;
    }

    public BigInteger getMajorUnits() {
        return this.majorUnits;
    }

    public int getMinorUnits() {
        return this.minorUnits;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    @Override
    public String toString() {
        return String.format("%d.%2d", this.majorUnits, this.minorUnits);
    }
}
