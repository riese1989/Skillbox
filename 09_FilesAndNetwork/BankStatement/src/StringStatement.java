import java.util.Date;

public class StringStatement {
    private String type;
    private String numberAccount;
    private String currency;
    private Date operationDate;
    private String reference;
    private String description;
    private Double coming;
    private Double consumption;

    public StringStatement(String type, String numberAccount, String currency, Date operationDate, String reference, String description, Double coming, Double consumption) {
        this.type = type;
        this.numberAccount = numberAccount;
        this.currency = currency;
        this.operationDate = operationDate;
        this.reference = reference;
        this.description = description;
        this.coming = coming;
        this.consumption = consumption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }
    public String getShortDescription() {
        String[] shortDescription = description.split("[/\\\\]");
        return shortDescription[shortDescription.length-1].split("  ")[0];
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getComing() {
        return coming;
    }

    public void setComing(Double coming) {
        this.coming = coming;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }
}
