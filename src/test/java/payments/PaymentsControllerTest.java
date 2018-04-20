package payments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import payments.attributes.Amount;
import payments.attributes.Attributes;
import payments.attributes.Currency;
import payments.attributes.parties.Account;
import payments.attributes.parties.Parties;
import payments.attributes.parties.Party;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class PaymentsControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private PaymentsRepository paymentsRepository;
    @Autowired
    private WebApplicationContext context;

    private Payment payment;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        String fullName = "First Middle Last";
        Amount amount = new Amount(BigInteger.valueOf(2), 56, Currency.GBP);
        Account account = new Account(fullName, "accNum", "code", 42);
        Party beneficiary = new Party(fullName, account);
        Parties parties = new Parties(beneficiary);
        Attributes attributes = new Attributes(amount, parties);
        payment = new Payment(attributes);

        this.paymentsRepository.save(payment);
    }

    @Test
    public void getSinglePayment() throws Exception {
        this.mockMvc.perform(get("/payments/" + this.payment.getId()))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.attributes.amount",
                        is(payment.getAttributes().getAmount().toString())))

                .andExpect(jsonPath("$.attributes.currency",
                        is(payment.getAttributes().getAmount().getCurrency().toString())))

                .andExpect(jsonPath("$.attributes.beneficiary_party.name",
                        is(payment.getAttributes().getParties().getBeneficiary().getName())))

                .andExpect(jsonPath("$.attributes.beneficiary_party.account_name",
                        is(payment.getAttributes().getParties().getBeneficiary().getAccount().getName())))

                .andExpect(jsonPath("$.attributes.beneficiary_party.account_number",
                        is(payment.getAttributes().getParties().getBeneficiary().getAccount().getNumber())))

                .andExpect(jsonPath("$.attributes.beneficiary_party.account_number_code",
                        is(payment.getAttributes().getParties().getBeneficiary().getAccount().getNumberCode())))

                .andExpect(jsonPath("$.attributes.beneficiary_party.account_type",
                        is(payment.getAttributes().getParties().getBeneficiary().getAccount().getType())))

                .andExpect(jsonPath("$.id", is(String.valueOf(payment.getId()))));
    }
}
