package payments;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Embeddable;

@Embeddable
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonSerialize(using = PartySerializer.class)
public class Party {
    private String name;
    private Account account;

    public Party() {
    }

    public Party(String name, Account account) {
        this.name = name;
        this.account = account;
    }

    public String getName() {
        return this.name;
    }

    public Account getAccount() {
        return account;
    }
}
