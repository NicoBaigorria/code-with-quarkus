package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.acme.entity.Project;


@Entity
@Table(name = "projectdetail")
public class ProjectDetail extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "CodigoProyecto", nullable = false)
    public Project project;

    @Column(name = "Descripcion", nullable = false)
    public String description;

    @Column(name = "Area", nullable = false)
    public int area;

    @Column(name = "Estado", nullable = false)
    public boolean status;
}
