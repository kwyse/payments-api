package payments.attributes.parties;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Account {
    @Column(name = "account_name")
    private String name;
    @Column(name = "account_number")
    private String number;
    @Column(name = "account_number_code")
    private String numberCode;
    @Column(name = "type")
    private int type;

    public Account() {
    }

    public Account(String number) {
        this.number = number;
    }

    public Account(String name, String number, String numberCode) {
        this.name = name;
        this.number = number;
        this.numberCode = numberCode;
    }

    public Account(String name, String number, String numberCode, int type) {
        this.name = name;
        this.number = number;
        this.numberCode = numberCode;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public String getNumber() {
        return number;
    }

    public String getNumberCode() {
        return numberCode;
    }

    public int getType() {
        return type;
    }
}
