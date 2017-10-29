package siosio.webexample.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity(immutable = true)
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private final Long projectId;

    private final String name;

    private final String type;

    @Column(name = "client_id")
    private final Long clientId;

    public ProjectEntity(final Long projectId, final String name, final String type, final Long clientId) {
        this.projectId = projectId;
        this.name = name;
        this.type = type;
        this.clientId = clientId;
    }
}
