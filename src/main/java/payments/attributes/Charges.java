package payments.attributes;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonSerialize(using = ChargesSerializer.class)
public class Charges {
    @Id
    @GeneratedValue
    private long id;

    private String bearerCode;
    @ElementCollection(targetClass = Amount.class)
    private List<Amount> senderCharges;
    private Amount receiverCharges;

    public Charges() {
    }

    public Charges(String bearerCode, List<Amount> senderCharges, Amount receiverCharges) {
        this.bearerCode = bearerCode;
        this.senderCharges = senderCharges;
        this.receiverCharges = receiverCharges;
    }

    public String getBearerCode() {
        return bearerCode;
    }

    public List<Amount> getSenderCharges() {
        return senderCharges;
    }

    public Amount getReceiverCharges() {
        return receiverCharges;
    }
}
