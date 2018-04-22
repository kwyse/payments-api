package payments.attributes;

import org.junit.Test;

import static org.junit.Assert.*;

public class ForeignExchangeTest {
    @Test
    public void constructStringRepresentationOfExchangeRate() {
        ForeignExchange fx = new ForeignExchange("", 2.0, new Amount());
        assertEquals(fx.getExchangeRateAsString(), "2.00000");

        fx = new ForeignExchange("", 12.123456, new Amount());
        assertEquals(fx.getExchangeRateAsString(), "12.12346");
    }
}
