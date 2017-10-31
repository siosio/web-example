package siosio.webexample.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import siosio.webexample.domain.ProjectType;

@Entity(immutable = true)
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    public final Long projectId;

    public final String name;

    public final ProjectType type;

    @Column(name = "client_id")
    public final Long clientId;

    public ProjectEntity(final String name, final ProjectType type, final Long clientId) {
        this(null, name, type, clientId);
    }

    public ProjectEntity(final Long projectId, final String name, final ProjectType type, final Long clientId) {
        this.projectId = projectId;
        this.name = name;
        this.type = type;
        this.clientId = clientId;
    }
}
