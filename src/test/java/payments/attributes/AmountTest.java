package payments.attributes;

import org.junit.Test;

import static org.junit.Assert.*;

import java.math.BigInteger;

public class AmountTest {
    @Test
    public void constructStringRepresentation() {
        Amount amount = new Amount(null, 0, null);
        assertEquals("0.00", amount.toString());

        amount = new Amount(BigInteger.valueOf(0), 0, Currency.GBP);
        assertEquals("0.00", amount.toString());

        amount = new Amount(BigInteger.valueOf(2), 5, Currency.GBP);
        assertEquals("2.05", amount.toString());

        amount = new Amount(BigInteger.valueOf(3), 30, Currency.GBP);
        assertEquals("3.30", amount.toString());

        amount = new Amount(BigInteger.valueOf(0), 408, Currency.GBP);
        assertEquals("4.08", amount.toString());
    }
}
