package payments.attributes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ForeignExchangeSerializer extends StdSerializer<ForeignExchange> {
    public ForeignExchangeSerializer() {
        this(null);
    }

    public ForeignExchangeSerializer(Class<ForeignExchange> t) {
        super(t);
    }

    @Override
    public void serialize(
            ForeignExchange value, JsonGenerator gen, SerializerProvider provider
    ) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("contract_reference", value.getContractReference());
        gen.writeStringField("exchange_rate", value.getExchangeRateAsString());
        gen.writeStringField("original_amount", value.getOriginalAmount().toString());
        gen.writeStringField("original_currency", value.getOriginalAmount().getCurrency().toString());

        gen.writeEndObject();
    }
}
