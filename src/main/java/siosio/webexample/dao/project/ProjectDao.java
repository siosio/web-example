package siosio.webexample.dao.project;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.jdbc.Result;
import siosio.webexample.entity.ProjectEntity;

@Dao
public interface ProjectDao {

    @Insert
    Result<ProjectEntity> insert(ProjectEntity project);

}
