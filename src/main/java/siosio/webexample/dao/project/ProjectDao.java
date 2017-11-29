package siosio.webexample.dao.project;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;
import siosio.webexample.entity.ProjectEntity;
import siosio.webexample.entity.ProjectPeriodEntity;

@ConfigAutowireable
@Dao
public interface ProjectDao {

    @Insert
    Result<ProjectEntity> insert(@NotNull final ProjectEntity project);

    @Insert
    Result<ProjectPeriodEntity> insertProjectPeriod(@NotNull final ProjectPeriodEntity projectPeriod);

    @Select
    List<ProjectEntity> search();
}
