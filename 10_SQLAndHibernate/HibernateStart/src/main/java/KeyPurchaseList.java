import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class KeyPurchaseList implements Serializable {
    @Column(name = "price")
    private int price;
    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyPurchaseList that = (KeyPurchaseList) o;
        return Objects.equals(price, that.price) &&
                Objects.equals(subscriptionDate, that.subscriptionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, subscriptionDate);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public KeyPurchaseList() {
    }
}
