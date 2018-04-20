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
import payments.attributes.parties.*;

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
    private PartyRepository partyRepository;
    @Autowired
    private WebApplicationContext context;

    private Payment payment;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        String fullName = "First Middle Last";
        String address = "42 Main St London EC42 G4D";
        Amount amount = new Amount(BigInteger.valueOf(2), 56, Currency.GBP);

        Account beneficiaryAccount = new Account("F Last", "accNum", "accCode", 42);
        Bank beneficiaryBank = new Bank("beneficiaryBankId", "beneficiaryBankIdCode");
        Party beneficiary = new Party(beneficiaryAccount, address, beneficiaryBank, fullName);
        this.partyRepository.save(beneficiary);

        Account debtorAccount = new Account("debtorName", "debtorAccNum", "debtorCode");
        Bank debtorBank = new Bank("debtorBankId", "debtorBankIdCode");
        Party debtor = new Party(debtorAccount, "111 The Circle", debtorBank, "Debt Name");
        this.partyRepository.save(debtor);

        Account sponsorAccount = new Account("sponsorAccNum");
        Bank sponsorBank = new Bank("sponsorBankId", "sponsorBankIdCode");
        Party sponsor = new Party(sponsorAccount, sponsorBank);
        this.partyRepository.save(sponsor);

        Parties parties = new Parties(beneficiary, debtor, sponsor);
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

                // Beneficiary party
                .andExpect(jsonPath("$.attributes.beneficiary_party.account_name",
                        is(payment.getAttributes().getParties().getBeneficiary().getAccount().getName())))
                .andExpect(jsonPath("$.attributes.beneficiary_party.account_number",
                        is(payment.getAttributes().getParties().getBeneficiary().getAccount().getNumber())))
                .andExpect(jsonPath("$.attributes.beneficiary_party.account_number_code",
                        is(payment.getAttributes().getParties().getBeneficiary().getAccount().getNumberCode())))
                .andExpect(jsonPath("$.attributes.beneficiary_party.account_type",
                        is(payment.getAttributes().getParties().getBeneficiary().getAccount().getType())))

                .andExpect(jsonPath("$.attributes.beneficiary_party.address",
                        is(payment.getAttributes().getParties().getBeneficiary().getAddress())))

                .andExpect(jsonPath("$.attributes.beneficiary_party.bank_id",
                        is(payment.getAttributes().getParties().getBeneficiary().getBank().getId())))
                .andExpect(jsonPath("$.attributes.beneficiary_party.bank_id_code",
                        is(payment.getAttributes().getParties().getBeneficiary().getBank().getIdCode())))

                .andExpect(jsonPath("$.attributes.beneficiary_party.name",
                        is(payment.getAttributes().getParties().getBeneficiary().getName())))

                // Debtor party
                .andExpect(jsonPath("$.attributes.debtor_party.account_name",
                        is(payment.getAttributes().getParties().getDebtor().getAccount().getName())))
                .andExpect(jsonPath("$.attributes.debtor_party.account_number",
                        is(payment.getAttributes().getParties().getDebtor().getAccount().getNumber())))
                .andExpect(jsonPath("$.attributes.debtor_party.account_number_code",
                        is(payment.getAttributes().getParties().getDebtor().getAccount().getNumberCode())))

                .andExpect(jsonPath("$.attributes.debtor_party.address",
                        is(payment.getAttributes().getParties().getDebtor().getAddress())))

                .andExpect(jsonPath("$.attributes.debtor_party.bank_id",
                        is(payment.getAttributes().getParties().getDebtor().getBank().getId())))
                .andExpect(jsonPath("$.attributes.debtor_party.bank_id_code",
                        is(payment.getAttributes().getParties().getDebtor().getBank().getIdCode())))

                .andExpect(jsonPath("$.attributes.debtor_party.name",
                        is(payment.getAttributes().getParties().getDebtor().getName())))

                // Sponsor party
                .andExpect(jsonPath("$.attributes.sponsor_party.account_number",
                        is(payment.getAttributes().getParties().getSponsor().getAccount().getNumber())))
                .andExpect(jsonPath("$.attributes.sponsor_party.bank_id",
                        is(payment.getAttributes().getParties().getSponsor().getBank().getId())))
                .andExpect(jsonPath("$.attributes.sponsor_party.bank_id_code",
                        is(payment.getAttributes().getParties().getSponsor().getBank().getIdCode())))

                .andExpect(jsonPath("$.id", is(String.valueOf(payment.getId()))));
    }
}
