package payments;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Account {
    @Column(name = "account_name")
    private String name;

    public Account() {
    }

    public Account(String fullName) {
        this.name = this.getFirstInitialWithLastName(fullName);
    }

    public String getName() {
        return this.name;
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
