package payments.attributes.parties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonSerialize(using = PartySerializer.class)
public class Party {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    private Account account;
    private String address;
    private Bank bank;
    private String name;

    public Party() {
    }

    public Party(Account account, Bank bank) {
        this.account = account;
        this.bank = bank;
    }

    public Party(Account account, String address, Bank bank, String name) {
        this.account = account;
        this.address = address;
        this.bank = bank;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public String getAddress() {
        return address;
    }

    public Bank getBank() {
        return bank;
    }

    public String getName() {
        return this.name;
    }
}
