package payments.attributes.parties;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccountTest {
    @Test
    public void constructName() {
        Account account = new Account(null, null, null, 0);
        assertNull(account.getName());

        account = new Account("", null, null, 0);
        assertEquals(account.getName(), "");

        account = new Account("Last", null, null, 0);
        assertEquals(account.getName(), "Last");

        account = new Account("First Last", null, null, 0);
        assertEquals(account.getName(), "F Last");

        account = new Account("First Middle Last", null, null, 0);
        assertEquals(account.getName(), "F Last");
    }
}
