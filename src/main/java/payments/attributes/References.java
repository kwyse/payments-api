package payments.attributes;

import javax.persistence.Embeddable;

@Embeddable
public class References {
    private String root;
    private String endToEnd;
    private String numeric;

    public References() {
    }

    public References(String root, String endToEnd, String numeric) {
        this.root = root;
        this.endToEnd = endToEnd;
        this.numeric = numeric;
    }

    public String getRoot() {
        return root;
    }

    public String getEndToEnd() {
        return endToEnd;
    }

    public String getNumeric() {
        return numeric;
    }
}
