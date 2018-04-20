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

    private String name;
    private Account account;
    private Bank bank;

    public Party() {
    }

    public Party(Account account, Bank bank) {
        this.account = account;
        this.bank = bank;
    }

    public Party(String name, Account account, Bank bank) {
        this.name = name;
        this.account = account;
        this.bank = bank;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public Account getAccount() {
        return account;
    }

    public Bank getBank() {
        return bank;
    }
}
