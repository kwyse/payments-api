package payments.attributes;

import javax.persistence.Embeddable;
import java.math.BigInteger;

@Embeddable
public class Amount {
    private BigInteger majorUnits;
    private int minorUnits;
    private Currency currency;

    public Amount() {
    }

    public Amount(BigInteger majorUnits, int minorUnits, Currency currency) {
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
        return String.format("%s.%2d", this.majorUnits.toString(), this.minorUnits);
    }
}
