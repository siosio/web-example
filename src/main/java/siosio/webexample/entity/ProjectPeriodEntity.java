package siosio.webexample.entity;

import java.time.LocalDate;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity(immutable = true)
@Table(name = "project_period")
public class ProjectPeriodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Long id;

    @Column(name = "project_id")
    public final Long projectId;

    @Column(name = "start_date")
    public final LocalDate startDate;

    @Column(name = "end_date")
    public final LocalDate endDate;

    public ProjectPeriodEntity(final Long projectId, final LocalDate startDate, final LocalDate endDate) {
        this(null, projectId, startDate, endDate);
    }

    public ProjectPeriodEntity(final Long id, final Long projectId, final LocalDate startDate,
            final LocalDate endDate) {
        this.id = id;
        this.projectId = projectId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

