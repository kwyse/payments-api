package payments.attributes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ChargesSerializer extends StdSerializer<Charges> {
    public ChargesSerializer() {
        this(null);
    }

    public ChargesSerializer(Class<Charges> t) {
        super(t);
    }

    @Override
    public void serialize(
            Charges value, JsonGenerator gen, SerializerProvider provider
    ) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("bearer_code", value.getBearerCode());
        gen.writeObjectField("sender_charges", value.getSenderCharges());
        gen.writeStringField("receiver_charges_amount", value.getReceiverCharges().toString());
        gen.writeStringField("receiver_charges_currency", value.getReceiverCharges().getCurrency().toString());

        gen.writeEndObject();
    }
}
