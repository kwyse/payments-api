package payments.attributes.parties;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Bank {
    @Column(name = "bank_id")
    private String id;
    @Column(name = "bank_id_code")
    private String idCode;

    public Bank() {
    }

    public Bank(String id, String idCode) {
        this.id = id;
        this.idCode = idCode;
    }

    public String getId() {
        return this.id;
    }

    public String getIdCode() {
        return this.idCode;
    }
}
