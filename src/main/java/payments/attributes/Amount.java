package payments.attributes;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Embeddable;
import java.math.BigInteger;

@Embeddable
@JsonSerialize(using = AmountSerializer.class)
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
        return String.format("%s.%02d", this.majorUnits.toString(), this.minorUnits);
    }
}
