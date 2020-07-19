import javax.persistence.*;

@Entity
@Table(name = "LinkedPurchaseList")

public class LinkedPurchaseList {
    @EmbeddedId
    private Key id;

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }
}
