package siosio.webexample.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity(immutable = true)
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private final Long clientId;

    private final String name;

    public ClientEntity(final Long clientId, final String name) {
        this.clientId = clientId;
        this.name = name;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }
}
