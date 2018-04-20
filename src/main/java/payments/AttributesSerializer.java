package payments;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

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
        gen.writeStartObject();

        gen.writeStringField("amount", value.getAmount().toString());
        gen.writeStringField("currency", String.valueOf(value.getAmount().getCurrency()));
        gen.writeObjectField("beneficiary_party", value.getParties().getBeneficiary());

        gen.writeEndObject();
    }
}
