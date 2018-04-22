package payments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import payments.attributes.*;
import payments.attributes.amount.Amount;
import payments.attributes.amount.Currency;
import payments.attributes.amount.fx.ForeignExchange;
import payments.attributes.amount.fx.ForeignExchangeRepository;
import payments.attributes.details.PaymentDetails;
import payments.attributes.details.PaymentDetailsScheme;
import payments.attributes.details.PaymentDetailsType;
import payments.attributes.parties.*;
import payments.attributes.scheme.SchemePayment;
import payments.attributes.scheme.SchemePaymentSubtype;
import payments.attributes.scheme.SchemePaymentType;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
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
    private ForeignExchangeRepository foreignExchangeRepository;
    @Autowired
    private WebApplicationContext context;

    private HttpMessageConverter jsonMapper;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType());

    private Payment payment;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.jsonMapper = Arrays.stream(converters)
                .filter(messageConverter -> messageConverter instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.paymentsRepository.deleteAllInBatch();

        payment = this.generatePayment();
        this.paymentsRepository.save(payment);
    }

    @Test
    public void getSingleExistingPayment() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        this.mockMvc.perform(get("/payments/" + this.payment.getId()))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.payment.attributes.amount",
                        is(payment.getAttributes().getAmount().toString())))
                .andExpect(jsonPath("$.payment.attributes.currency",
                        is(payment.getAttributes().getAmount().getCurrency().toString())))

                // Beneficiary party
                .andExpect(jsonPath("$.payment.attributes.beneficiary_party.account_name",
                        is(payment.getAttributes().getParties().getBeneficiary().getAccount().getName())))
                .andExpect(jsonPath("$.payment.attributes.beneficiary_party.account_number",
                        is(payment.getAttributes().getParties().getBeneficiary().getAccount().getNumber())))
                .andExpect(jsonPath("$.payment.attributes.beneficiary_party.account_number_code",
                        is(payment.getAttributes().getParties().getBeneficiary().getAccount().getNumberCode())))
                .andExpect(jsonPath("$.payment.attributes.beneficiary_party.account_type",
                        is(payment.getAttributes().getParties().getBeneficiary().getAccount().getType())))

                .andExpect(jsonPath("$.payment.attributes.beneficiary_party.address",
                        is(payment.getAttributes().getParties().getBeneficiary().getAddress())))

                .andExpect(jsonPath("$.payment.attributes.beneficiary_party.bank_id",
                        is(payment.getAttributes().getParties().getBeneficiary().getBank().getId())))
                .andExpect(jsonPath("$.payment.attributes.beneficiary_party.bank_id_code",
                        is(payment.getAttributes().getParties().getBeneficiary().getBank().getIdCode())))

                .andExpect(jsonPath("$.payment.attributes.beneficiary_party.name",
                        is(payment.getAttributes().getParties().getBeneficiary().getName())))

                // Debtor party
                .andExpect(jsonPath("$.payment.attributes.debtor_party.account_name",
                        is(payment.getAttributes().getParties().getDebtor().getAccount().getName())))
                .andExpect(jsonPath("$.payment.attributes.debtor_party.account_number",
                        is(payment.getAttributes().getParties().getDebtor().getAccount().getNumber())))
                .andExpect(jsonPath("$.payment.attributes.debtor_party.account_number_code",
                        is(payment.getAttributes().getParties().getDebtor().getAccount().getNumberCode())))

                .andExpect(jsonPath("$.payment.attributes.debtor_party.address",
                        is(payment.getAttributes().getParties().getDebtor().getAddress())))

                .andExpect(jsonPath("$.payment.attributes.debtor_party.bank_id",
                        is(payment.getAttributes().getParties().getDebtor().getBank().getId())))
                .andExpect(jsonPath("$.payment.attributes.debtor_party.bank_id_code",
                        is(payment.getAttributes().getParties().getDebtor().getBank().getIdCode())))

                .andExpect(jsonPath("$.payment.attributes.debtor_party.name",
                        is(payment.getAttributes().getParties().getDebtor().getName())))

                // Sponsor party
                .andExpect(jsonPath("$.payment.attributes.sponsor_party.account_number",
                        is(payment.getAttributes().getParties().getSponsor().getAccount().getNumber())))
                .andExpect(jsonPath("$.payment.attributes.sponsor_party.bank_id",
                        is(payment.getAttributes().getParties().getSponsor().getBank().getId())))
                .andExpect(jsonPath("$.payment.attributes.sponsor_party.bank_id_code",
                        is(payment.getAttributes().getParties().getSponsor().getBank().getIdCode())))

                // Charges
                .andExpect(jsonPath("$.payment.attributes.charges_information.bearer_code",
                        is(payment.getAttributes().getCharges().getBearerCode())))
                .andExpect(jsonPath("$.payment.attributes.charges_information.sender_charges[0].amount",
                        is(payment.getAttributes().getCharges().getSenderCharges().get(0).toString())))
                .andExpect(jsonPath("$.payment.attributes.charges_information.sender_charges[0].currency",
                        is(payment.getAttributes().getCharges().getSenderCharges().get(0).getCurrency().toString())))
                .andExpect(jsonPath("$.payment.attributes.charges_information.sender_charges[1].amount",
                        is(payment.getAttributes().getCharges().getSenderCharges().get(1).toString())))
                .andExpect(jsonPath("$.payment.attributes.charges_information.sender_charges[1].currency",
                        is(payment.getAttributes().getCharges().getSenderCharges().get(1).getCurrency().toString())))
                .andExpect(jsonPath("$.payment.attributes.charges_information.receiver_charges_amount",
                        is(payment.getAttributes().getCharges().getReceiverCharges().toString())))
                .andExpect(jsonPath("$.payment.attributes.charges_information.receiver_charges_currency",
                        is(payment.getAttributes().getCharges().getReceiverCharges().getCurrency().toString())))

                // References
                .andExpect(jsonPath("$.payment.attributes.reference",
                        is(payment.getAttributes().getReferences().getRoot())))
                .andExpect(jsonPath("$.payment.attributes.end_to_end_reference",
                        is(payment.getAttributes().getReferences().getEndToEnd())))
                .andExpect(jsonPath("$.payment.attributes.numeric_reference",
                        is(payment.getAttributes().getReferences().getNumeric())))

                // Payment details
                .andExpect(jsonPath("$.payment.attributes.payment_id",
                        is(payment.getAttributes().getPaymentDetails().getId())))
                .andExpect(jsonPath("$.payment.attributes.payment_purpose",
                        is(payment.getAttributes().getPaymentDetails().getPurpose())))
                .andExpect(jsonPath("$.payment.attributes.payment_scheme",
                        is(payment.getAttributes().getPaymentDetails().getScheme().toString())))
                .andExpect(jsonPath("$.payment.attributes.payment_type",
                        is(payment.getAttributes().getPaymentDetails().getType().toString())))

                // Foreign Exchange
                .andExpect(jsonPath("$.payment.attributes.fx.contract_reference",
                        is(payment.getAttributes().getForeignExchange().getContractReference())))
                .andExpect(jsonPath("$.payment.attributes.fx.exchange_rate",
                        is(payment.getAttributes().getForeignExchange().getExchangeRateAsString())))
                .andExpect(jsonPath("$.payment.attributes.fx.original_amount",
                        is(payment.getAttributes().getForeignExchange().getOriginalAmount().toString())))
                .andExpect(jsonPath("$.payment.attributes.fx.original_currency",
                        is(payment.getAttributes().getForeignExchange().getOriginalAmount().getCurrency().toString())))

                // Scheme Payment
                .andExpect(jsonPath("$.payment.attributes.scheme_payment_type",
                        is(payment.getAttributes().getSchemePayment().getType().toString())))
                .andExpect(jsonPath("$.payment.attributes.scheme_payment_sub_type",
                        is(payment.getAttributes().getSchemePayment().getSubtype().toString())))

                .andExpect(jsonPath("$.payment.attributes.processing_date",
                        is(dateFormat.format(payment.getAttributes().getProcessingDate()))))

                .andExpect(jsonPath("$.payment.id", is(this.payment.getId().toString())))
                .andExpect(jsonPath("$.payment.version", is(this.payment.getVersion())))
                .andExpect(jsonPath("$.payment.type", is(this.payment.getType().toString())))
                .andExpect(jsonPath("$.links[0].href", containsString("/payments/" + this.payment.getId())));
    }

    @Test
    public void getSingleNonExistingPayment() throws Exception {
        this.mockMvc.perform(get("/payments/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getListOfPayments() throws Exception {
        this.mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links[0].href", containsString("/payments")))
                .andExpect(jsonPath("$.content[0].payment.id", is(this.payment.getId().toString())));
    }

    @Test
    public void createNewPayment() throws Exception {
        Payment payment = new Payment();
        String paymentJson = this.encodeToJson(payment);

        long entityCount = this.paymentsRepository.count();
        this.mockMvc.perform(post("/payments").contentType(this.contentType).content(paymentJson))
                .andExpect(status().isCreated());

        assertEquals(entityCount + 1, this.paymentsRepository.count());
    }

    @Test
    public void updateExistingPayment() throws Exception {
        Payment payment = this.paymentsRepository.save(this.generatePayment());

        payment.setOrganisationId(UUID.randomUUID());
        String paymentJson = this.encodeToJson(payment);

        this.mockMvc.perform(put("/payments/" + payment.getId()).contentType(this.contentType).content(paymentJson))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateNonExistingPayment() throws Exception {
        this.mockMvc.perform(put("/payments/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteExistingPayment() throws Exception {
        Payment payment = this.paymentsRepository.save(this.generatePayment());
        long entityCount = this.paymentsRepository.count();

        this.mockMvc.perform(delete("/payments/" + payment.getId()))
                .andExpect(status().isNoContent());

        assertEquals(entityCount - 1, this.paymentsRepository.count());
    }

    @Test
    public void deleteNonExistingPayment() throws Exception {
        long entityCount = this.paymentsRepository.count();

        this.mockMvc.perform(delete("/payments/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());

        assertEquals(entityCount, this.paymentsRepository.count());
    }

    private String encodeToJson(Object o) throws IOException {
        MockHttpOutputMessage message = new MockHttpOutputMessage();
        this.jsonMapper.write(o, MediaType.APPLICATION_JSON, message);
        return message.getBodyAsString();
    }

    private Payment generatePayment() {
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
                new Amount(BigInteger.valueOf(5), 10, Currency.GBP),
                new Amount(BigInteger.valueOf(10), 20, Currency.GBP)
        );
        Amount receiverAmount = new Amount(BigInteger.valueOf(1), 30, Currency.GBP);
        Charges charges = new Charges("bearerCode", senderAmounts, receiverAmount);
        this.chargesRepository.save(charges);

        ForeignExchange foreignExchange = new ForeignExchange("contractRef", 2.0, new Amount(BigInteger.valueOf(200), 42, Currency.GBP));
        this.foreignExchangeRepository.save(foreignExchange);

        SchemePayment schemePayment = new SchemePayment(SchemePaymentType.ImmediatePayment, SchemePaymentSubtype.InternetBanking);

        Attributes attributes = new AttributesBuilder()
                .withAmount(amount)
                .withCharges(charges)
                .withForeignExchange(foreignExchange)
                .withParties(parties)
                .withPaymentDetails(paymentDetails)
                .withProcessingDate(processingDate)
                .withReferences(references)
                .withSchemePayment(schemePayment)
                .build();

        return new Payment(UUID.randomUUID(), 42, PaymentType.Payment, attributes);
    }
}
