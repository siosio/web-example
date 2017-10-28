package siosio.webexample.dao.user;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;
import siosio.webexample.entity.UserEntity;
import siosio.webexample.entity.UserRolesEntity;
import siosio.webexample.value.UserId;

@ConfigAutowireable
@Dao
public interface UserDao {

    @Select
    UserEntity findByUserId(UserId userId);

    @Select
    List<UserRolesEntity> findRolesByUserId(UserId userId);

    @Insert
    Result<UserEntity> insert(UserEntity entity);

}
