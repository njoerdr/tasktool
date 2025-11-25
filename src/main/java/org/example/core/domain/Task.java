package org.example.core.domain;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Task {

    private Long id;
    private String name;
    private boolean done;
    private Instant created;
    private Priority priority;

    public Task(Long id, String name, boolean done, Instant created, Priority priority) {
        this.id = id;
        this.name = name;
        this.done = done;
        this.created = created;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void done() {
        this.done = true;
    }

    public void undone() {
        this.done = false;
    }

    public boolean isDone() {
        return done;
    }

    public Instant getCreated() {
        return created;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public static Task createTask(String name, Priority priority) {
        return new Task(null, name, false, Instant.now().truncatedTo(ChronoUnit.SECONDS), priority);
    }
}
