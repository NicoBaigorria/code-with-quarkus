package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project")
public class Project extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;  // La clave primaria

    @Column(name = "uuid", nullable = false, unique = true)
    public String uuid;

    @Column(name = "nombre", nullable = false)
    public String nombre;

    @Column(name = "creationDate", nullable = false)
    public LocalDateTime createdAt;  // Aquí debe existir la propiedad

    @Column(name = "status", nullable = false)
    public boolean status;
}
