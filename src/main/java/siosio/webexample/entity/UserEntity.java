package siosio.webexample.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import siosio.webexample.value.UserId;

@Entity(immutable = true)
@Table(name = "user")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    private final UserId userId;

    private final String password;

    public UserEntity(final UserId userId, final String password) {
        this.userId = userId;
        this.password = password;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
