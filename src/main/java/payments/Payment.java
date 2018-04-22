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
    private UUID organisationId;
    private int version;

    @Embedded
    private Attributes attributes;

    public Payment() {
    }

    public Payment(UUID organisationId, int version, Attributes attributes) {
        this.organisationId = organisationId;
        this.version = version;
        this.attributes = attributes;
    }

    public UUID getId() {
        return id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public UUID getOrganisationId() {
        return organisationId;
    }

    public int getVersion() {
        return version;
    }

    public void setOrganisationId(UUID organisationId) {
        this.organisationId = organisationId;
    }
}
