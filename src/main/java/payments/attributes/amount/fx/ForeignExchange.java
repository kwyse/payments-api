package payments.attributes.amount.fx;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import payments.attributes.amount.Amount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@JsonSerialize(using = ForeignExchangeSerializer.class)
public class ForeignExchange {
    @Id
    @GeneratedValue
    private long id;

    private String contractReference;
    private double exchangeRate;
    private Amount originalAmount;

    public ForeignExchange() {
    }

    public ForeignExchange(String contractReference, double exchangeRate, Amount originalAmount) {
        this.contractReference = contractReference;
        this.exchangeRate = exchangeRate;
        this.originalAmount = originalAmount;
    }

    public long getId() {
        return id;
    }

    public String getContractReference() {
        return contractReference;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public Amount getOriginalAmount() {
        return originalAmount;
    }

    public String getExchangeRateAsString() {
        return String.format("%2.5f", this.exchangeRate);
    }
}
