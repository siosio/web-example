package siosio.webexample.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import siosio.webexample.value.UserId;

@Entity(immutable = true)
@Table(name = "user_roles")
public class UserRolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "user_id")
    private final UserId userId;

    private final String role;

    public UserRolesEntity(final Long id, final UserId userId, final String role) {
        this.id = id;
        this.userId = userId;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
