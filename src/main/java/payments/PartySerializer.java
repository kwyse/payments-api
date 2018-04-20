package payments;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PartySerializer extends StdSerializer<Party> {
    public PartySerializer() {
        this(null);
    }

    public PartySerializer(Class<Party> t) {
        super(t);
    }

    @Override
    public void serialize(
            Party value, JsonGenerator gen, SerializerProvider provider
    ) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("account_name", value.getAccount().getName());
        gen.writeStringField("name", value.getName());

        gen.writeEndObject();
    }
}
