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

    public Account(String fullName, String number, String numberCode, int type) {
        this.name = this.getFirstInitialWithLastName(fullName);
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

    private String getFirstInitialWithLastName(String fullName) {
        if (fullName == null) {
            return null;
        }

        String[] components = fullName.split(" ");

        if (components.length == 1) {
            return fullName;
        } else {
            char initial = components[0].charAt(0);
            String last = components[components.length - 1];

            return String.format("%c %s", initial, last);
        }
    }
}
