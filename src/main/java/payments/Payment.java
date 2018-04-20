package payments;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import payments.attributes.Attributes;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Payment {
    @Id
    @GeneratedValue
    private UUID id;

    @Embedded
    private Attributes attributes;

    public Payment() {
    }

    Payment(Attributes attributes) {
        this.attributes = attributes;
    }

    public UUID getId() {
        return id;
    }

    public Attributes getAttributes() {
        return attributes;
    }
}
