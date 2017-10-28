package siosio.webexample.value;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class UserId {

    private final String value;

    public UserId(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
