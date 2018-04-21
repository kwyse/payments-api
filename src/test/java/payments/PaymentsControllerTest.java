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
import payments.attributes.Charges;
import payments.attributes.ChargesRepository;
import payments.attributes.Currency;
import payments.attributes.References;
import payments.attributes.details.PaymentDetails;
import payments.attributes.details.PaymentDetailsScheme;
import payments.attributes.details.PaymentDetailsType;
import payments.attributes.parties.*;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private ChargesRepository chargesRepository;
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
        References references = new References("rootRef", "e2eRef", "numRef");
        Date processingDate = new GregorianCalendar(2017, Calendar.JANUARY, 24).getTime();
        PaymentDetails paymentDetails = new PaymentDetails("payId", "payPurpose", PaymentDetailsScheme.FPS, PaymentDetailsType.Credit);

        List<Amount> senderAmounts = Arrays.asList(
                new Amount(BigInteger.valueOf(5), 0, Currency.GBP),
                new Amount(BigInteger.valueOf(10), 0, Currency.GBP)
        );
        Amount receiverAmount = new Amount(BigInteger.valueOf(1), 0, Currency.GBP);
        Charges charges = new Charges("bearerCode", senderAmounts, receiverAmount);
        this.chargesRepository.save(charges);

        Attributes attributes = new Attributes(amount, parties, references, processingDate, paymentDetails, charges);
        payment = new Payment(attributes);

        this.paymentsRepository.save(payment);
    }

    @Test
    public void getSinglePayment() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

                // Charges
                .andExpect(jsonPath("$.attributes.charges_information.bearer_code",
                        is(payment.getAttributes().getCharges().getBearerCode())))
                .andExpect(jsonPath("$.attributes.charges_information.sender_charges[0].amount",
                        is(payment.getAttributes().getCharges().getSenderCharges().get(0).toString())))
                .andExpect(jsonPath("$.attributes.charges_information.sender_charges[0].currency",
                        is(payment.getAttributes().getCharges().getSenderCharges().get(0).getCurrency().toString())))
                .andExpect(jsonPath("$.attributes.charges_information.sender_charges[1].amount",
                        is(payment.getAttributes().getCharges().getSenderCharges().get(1).toString())))
                .andExpect(jsonPath("$.attributes.charges_information.sender_charges[1].currency",
                        is(payment.getAttributes().getCharges().getSenderCharges().get(1).getCurrency().toString())))
                .andExpect(jsonPath("$.attributes.charges_information.receiver_charges_amount",
                        is(payment.getAttributes().getCharges().getReceiverCharges().toString())))
                .andExpect(jsonPath("$.attributes.charges_information.receiver_charges_currency",
                        is(payment.getAttributes().getCharges().getReceiverCharges().getCurrency().toString())))

                // References
                .andExpect(jsonPath("$.attributes.reference",
                        is(payment.getAttributes().getReferences().getRoot())))
                .andExpect(jsonPath("$.attributes.end_to_end_reference",
                        is(payment.getAttributes().getReferences().getEndToEnd())))
                .andExpect(jsonPath("$.attributes.numeric_reference",
                        is(payment.getAttributes().getReferences().getNumeric())))

                // Payment details
                .andExpect(jsonPath("$.attributes.payment_id",
                        is(payment.getAttributes().getPaymentDetails().getId())))
                .andExpect(jsonPath("$.attributes.payment_purpose",
                        is(payment.getAttributes().getPaymentDetails().getPurpose())))
                .andExpect(jsonPath("$.attributes.payment_scheme",
                        is(payment.getAttributes().getPaymentDetails().getScheme().toString())))
                .andExpect(jsonPath("$.attributes.payment_type",
                        is(payment.getAttributes().getPaymentDetails().getType().toString())))

                .andExpect(jsonPath("$.attributes.processing_date",
                        is(dateFormat.format(payment.getAttributes().getProcessingDate()))))

                .andExpect(jsonPath("$.id", is(String.valueOf(payment.getId()))));
    }
}
