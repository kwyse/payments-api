package payments.attributes.amount;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Embeddable;
import java.math.BigInteger;

@Embeddable
@JsonSerialize(using = AmountSerializer.class)
public class Amount {
    private BigInteger majorUnits;
    private Integer minorUnits;
    private Currency currency;

    public Amount() {
    }

    public Amount(BigInteger majorUnits, int minorUnits, Currency currency) {
        if (majorUnits == null) majorUnits = BigInteger.ZERO;
        this.currency = currency;

        if (minorUnits > 99) {
            long extraMajorUnits = Math.floorDiv(minorUnits, 100);
            majorUnits = majorUnits.add(BigInteger.valueOf(extraMajorUnits));
            minorUnits %= 100;
        }

        this.majorUnits = majorUnits;
        this.minorUnits = minorUnits;
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
        return String.format("%d.%02d", this.majorUnits, this.minorUnits);
    }
}
