package siosio.webexample.dao.project;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import siosio.webexample.entity.ClientEntity;

@ConfigAutowireable
@Dao
public interface ClientDao {

    @Select
    ClientEntity findById(final Long clientId);

    default boolean existsClient(final Long clientId) {
        return findById(clientId) != null;
    }

}
