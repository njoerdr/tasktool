package org.example.adapter.database;

import java.time.Instant;

import org.example.core.domain.Priority;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class TaskEntity extends PanacheEntity {
    public String name;
    public boolean done;
    public Instant created;
    public Priority priority;
}
