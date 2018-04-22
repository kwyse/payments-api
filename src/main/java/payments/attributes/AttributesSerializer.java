package payments.attributes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class AttributesSerializer extends StdSerializer<Attributes> {
    public AttributesSerializer() {
        this(null);
    }

    public AttributesSerializer(Class<Attributes> t) {
        super(t);
    }

    @Override
    public void serialize(
            Attributes value, JsonGenerator gen, SerializerProvider provider
    ) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        gen.writeStartObject();

        gen.writeStringField("amount", value.getAmount().toString());
        gen.writeStringField("currency", String.valueOf(value.getAmount().getCurrency()));

        gen.writeObjectField("beneficiary_party", value.getParties().getBeneficiary());
        gen.writeObjectField("debtor_party", value.getParties().getDebtor());
        gen.writeObjectField("sponsor_party", value.getParties().getSponsor());

        gen.writeStringField("reference",  value.getReferences().getRoot());
        gen.writeStringField("end_to_end_reference",  value.getReferences().getEndToEnd());
        gen.writeStringField("numeric_reference",  value.getReferences().getNumeric());

        gen.writeStringField("processing_date", dateFormat.format(value.getProcessingDate()));

        gen.writeStringField("payment_id", value.getPaymentDetails().getId());
        gen.writeStringField("payment_purpose", value.getPaymentDetails().getPurpose());
        gen.writeStringField("payment_scheme", value.getPaymentDetails().getScheme().toString());
        gen.writeStringField("payment_type", value.getPaymentDetails().getType().toString());

        gen.writeObjectField("charges_information", value.getCharges());

        gen.writeObjectField("fx", value.getForeignExchange());

        gen.writeEndObject();
    }
}
